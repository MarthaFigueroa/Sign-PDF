import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom'
import * as Icons from "react-icons/ai";
import axios from '../../axios.js';
import { firestore, storage } from '../../Config/config';
import PassInput from '../Partials/PassInput';

const UploadFilesForm = ({ message }) => {    
    const [file , setFile] = useState();
    const [cert , setCert] = useState();
    const [certs , setCerts] = useState([]);
    const [existingFile , setExistingFile] = useState(false);
    const [certPass , setCertPass] = useState([]);
    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }
    const handleCreatedCert = async (e) =>{
        const filename = e.target.value;
        console.log(filename);

        if(filename !== "Seleccione un certificado"){
            const certName = certs.filter(cert => cert.Filename === filename);
            console.log(certName);
            setCert(certName);
        }else{
            setCert(null);
        }
    }    
    const handleUploadedFile = (e) =>{
        // e.preventDefault();
        console.log(e.target.files[0]);
        firestore.collection('documents').where("OriginalFile.Filename", '==', e.target.files[0].name).get()
        .then(async (querySnapshot) => {
            // total matched documents
            const matchedDocs = querySnapshot.size
            if (matchedDocs) {
                // querySnapshot.docs.forEach(async doc => {
                //     console.log(doc.id, "=>", doc.data())
                // })
                await message("El documento que desea firmar ya existe", "warning");
                setExistingFile(true);
                setFile(null);
            } else {
                console.log("0 documents matched the query");
                setExistingFile(false);
                setFile(e.target.files[0]);
            }
        })
    }
    const convertSignedFileToBlob = async (signed) =>{
        await axios.get(`/file/${signed}`, {
            headers: {
                Accept: "application/json ,text/plain, */*"
            },
            responseType:"blob"
        })
        .then(async res => {
            const signedDoc = new Blob([res.data]);
            let formDataSigned = new FormData();
            formDataSigned.append("file", signedDoc);
            formDataSigned.append('data', new Blob([JSON.stringify({
                "filename": signed
            })], {
                type: "application/json"
            }));

            console.log("Almost uploaded");

            await axios.post('/uploadSigned', formDataSigned, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                },
            }).then(async res => {
                console.log("Response Data File",res.data);
                goTo('/documents');
            });
        }, [])
    }
    const signingProcess = async (filesData) =>{
        await axios.post(`/sign`, filesData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(async res => {
            console.log("Response Data",res.data);
            if(res.data.Error){
                if(res.data.Error.message === "The specified network password is not correct." || res.data.Error === "The specified network password is not correct."){
                    await message("La clave del Certificado es incorrecta", "error");
                }
            }else if(!res.data.preSigned){
                console.log("Response File Data",res.data.FileData);
                const FileData = res.data.FileData;
    
                const OriginalFile = {
                    "Filename": FileData.Filename,
                    "File_hash": FileData.File_hash,
                    "File": FileData.File,
                    "Created_at": FileData.Created_at,
                    "LastModified": FileData.LastModified_originalFile,
                    "Size": FileData.Size_originalFile,
                    "Type": FileData.Type_originalFile
                }
    
                const SignedFile = {
                    "Filename": FileData.Signed_filename,
                    "CSV": FileData.CSV,
                    "Signers": FileData.Signers,
                    "File_hash": FileData.Signed_file_hash,
                    "File": FileData.Signed_file,
                    "Created_at": FileData.Created_at,
                    "LastModified": FileData.LastModified_signedFile,
                    "Size": FileData.Size_signedFile,
                    "Type": FileData.Type_signedFile,
                }
    
                const docData = {
                    OriginalFile,
                    SignedFile
                }
    
                console.log("Doc Data",docData);

                await axios.post(`/createDocument`, docData, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                })
                .then(async res => {
                    console.log("Response Data",res.data);
                })
                const signed = res.data.FileData.Signed_filename;
                
                await convertSignedFileToBlob(signed);
                const arr = [];
                storage.ref().child('certificates/').listAll()
                .then(async res => {
                    res.items.forEach((item) => {
                        arr.push(item.name);
                    })
                    if(arr.length === 0){
                        await storageDoc(`/certificates/${cert.name}`, cert, cert.name);
                    }
                })
                .catch(err => {
                    alert(err.message);
                })
                console.log(arr);
                message(`Se ha firmado un nuevo Documento: ${file.name}`, "success")
                
            }else{
                message("El documento que ha intentado firmar ya está firmado", "warning");
            }
        },(error) => { 
            console.log("F:",error) 
        })
    }
    const signDoc = async ()=>{
        if(file == null)
        return;

        const metadata = {
            name: String(file.name),
            lastModified: String(file.lastModified),
            size: file.size,
            type: String(file.type)
        }

        const certMetadata = {
            name: String(cert[0].Filename),
            lastModified: String(cert[0].LastModified),
            size: cert[0].Size,
            type: String(cert[0].type),
            certPass: String(certPass)
        }

        const newData = new Blob([JSON.stringify({
            "docMetadata": metadata,
            "certMetadata": certMetadata,
        })], {
            type: "application/json"
        })

        console.log("Cert:",cert);
        console.log("file:",file);
        const filesData = new FormData();
        filesData.append("file", file);
        filesData.append('data', newData);
        
        if(cert !== null || file !== null){
            console.log(newData);
            signingProcess(filesData);
        }
    }    
    const storageDoc = async (url, doc, filename) => {
        console.log(url);
        storage.ref(url).put(doc)
            .on("state_changed" , 
            console.log(`success uploading ${filename}`),
            message(`Se ha firmado un nuevo Documento: ${filename}`, "success")
        );
    }    
    useEffect(() => {
        const getCerts = () =>{
            const certificates = [];
            firestore.collection('certificates').onSnapshot(async snapshot => {
              snapshot.forEach((cert, index) => {
                certificates.push({...cert.data(), id: cert.id});
              });
              await setCerts(certificates);
            });
        }
        getCerts();
    }, []);
    
    return(
        <>            
            <div className="relative items-stretch w-full flex-nowrap border-none">
                <label htmlFor="">Seleccione el a Documento a Firmar</label>
                <input type="file" id='file-selector' className='box-border p-3 w-2/3 leading-6 text-justify' onChange={handleUploadedFile} accept=".pdf"/>
                {existingFile ? 
                    <span className='text-yellow-500 inline-flex text-xl'>
                        <Icons.AiFillWarning />
                    </span>: 
                    null
                }
            </div>
            <div className="relative items-stretch w-full flex-nowrap border-none">
                <label htmlFor="">Seleccione un certificado para firmar el documento</label>
                <div className="form-row text-center w-full form-fields">
                    <select className='form-select mb-5 block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding bg-no-repeat border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none' name="id_cert" key="id_cert" onChange={handleCreatedCert} > 
                        <option key='Seleccione un certificado' value="Seleccione un certificado">Seleccione un certificado</option>
                        {certs.map( (cert, index) => (
                            <option key={index} value={cert.Filename}>{cert.Signers}</option>
                        ))}
                    </select>
                </div>
            </div> 
            <div className="input-div">
                <label htmlFor="">Inserte la clave del Certificado</label>
                <PassInput handlePass={setCertPass}/>
            </div>
            <div className="relative">
                <button onClick={() => goTo('/documents')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</button>
                <button className='btn-primary absolute right-0 top-0' onClick={signDoc} disabled={file===null || !cert || !certPass}>Firmar</button>
            </div>
        </>
    )
}
export default UploadFilesForm;

