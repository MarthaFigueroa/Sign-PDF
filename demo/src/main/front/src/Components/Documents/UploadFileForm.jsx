import React, {useEffect, useState, useContext} from 'react';
import { useNavigate } from 'react-router-dom'
import * as Icons from "react-icons/ai";
import axios from '../../axios.js';
import { firestore, storage } from '../../Config/config';
import { UserContext } from "../../Providers/UserProvider";
import PassInput from '../Partials/PassInput';

const UploadFilesForm = ({ message }) => {    
    const [file , setFile] = useState();
    const [cert , setCert] = useState();
    const [certs , setCerts] = useState([]);
    const [existingFile , setExistingFile] = useState(false);
    const [certPass , setCertPass] = useState([]);
    const user = useContext(UserContext);
    const navigate = useNavigate();
    const counts = {};

    const goTo = (route) =>{
        navigate(route);
    }
    const handleCreatedCert = async (e) =>{
        const filename = e.target.value;
        if(filename !== "Seleccione un certificado"){
            const certName = certs.filter(cert => cert.Filename === filename);
            setCert(certName);
        }else{
            setCert(null);
        }
    }    
    const handleUploadedCert = async (e) =>{
        setCert(e.target.files[0]);
    }
    const handleUploadedFile = (e) =>{
        firestore.collection('documents').where("OriginalFile.Filename", '==', e.target.files[0].name).get()
        .then(async (querySnapshot) => {
            // total matched documents
            const matchedDocs = querySnapshot.size
            if (matchedDocs) {
                await message("El documento que desea firmar ya existe", "warning");
                setExistingFile(true);
                setFile(null);
            } else {
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
                goTo('/documents');
            });
        }, [])
    }
    const signingProcess = async (filesData, certMetadata) =>{
        let loadingDiv = document.createElement('div');
        loadingDiv.className = "loader mx-auto";
        document.getElementById("signingFile").appendChild(loadingDiv);
        
        await axios.post(`/sign`, filesData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(async res => {
            if(res.data.Error){
                if(res.data.Error.message === "The specified network password is not correct." || res.data.Error === "The specified network password is not correct."){
                    await message("La clave del Certificado es incorrecta", "error");
                    console.log("PPP");
                    loadingDiv.style.display = "none";
                }
            }else if(!res.data.preSigned){
                console.log("Not Signed");

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
                firestore.collection('certificates').where("Filename", '==', certMetadata.name).get()
                .then(async (querySnapshot) => {
                    const matchedDocs = querySnapshot.size
                    if (!matchedDocs) {
                        let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/certificates/${certMetadata.name}`);
                        await imageRef.delete();
                    }
                })

                await axios.post(`/createDocument`, docData, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
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
                await message(`Se ha firmado un nuevo Documento: ${file.name}`, "success")
                
            }else{
                console.log("Signed");
                await message("El documento que ha intentado firmar ya está firmado", "warning");
                loadingDiv.style.display = "none";
            }
        },(error) => { 
            console.error(error); 
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

        let certMetadata;
        if(cert[0]){
            certMetadata = {
                name: String(cert[0].Filename),
                lastModified: String(cert[0].LastModified),
                size: cert[0].Size,
                type: String(cert[0].type),
                certPass: String(certPass)
            }
        }else{
            certMetadata = {
                name: String(cert.name),
                lastModified: String(cert.lastModified),
                size: cert.size,
                type: String(cert.type),
                certPass: String(certPass)
            }
        }

        const newData = new Blob([JSON.stringify({
            "docMetadata": metadata,
            "certMetadata": certMetadata,
        })], {
            type: "application/json"
        })

        const filesData = new FormData();
        filesData.append("file", file);
        filesData.append('data', newData);
        console.log(file);

        if(!user){
    
            const certsData = new FormData();
            certsData.append("cert", cert);
            certsData.append('data', new Blob([JSON.stringify({
                "certMetadata": certMetadata,
            })], {
                type: "application/json"
            }));
            
            await axios.post(`/certs`, certsData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
                responseType:"application/x-pkcs12"
            })
            .then(async res => {
                const data = res.data;
                if(data.Error){
                    if(data.Error.message === "The specified network password is not correct."){
                        await message("La clave del Certificado es incorrecta", "error");
                    }
                }
    
                storage.ref().child('/certificates').listAll()
                .then(async res => {
                    const certNames = res.items.filter(item => item.name === data.Filename);
                    if(certNames.length === 0){
                        await uploadDoc(`/certificates/${certMetadata.name}`, cert, certMetadata.name);
                    }
                    goTo('/certificates');
                    message(`Se ha añadido un nuevo Certificado Digital: ${data.Filename}`, "success")
                })                        
                .catch(err => {
                    alert(err.message);
                })
            })
        }

        if(cert !== null || file !== null){
            signingProcess(filesData, certMetadata);
        }
    }   
    const uploadDoc = async (url, doc, filename) => {
        const metadata = {
            contentType: 'application/x-pkcs12',
            size: doc.size
        };
          
        // Create storage ref & put the file in it
        storage.ref(url).put(doc, metadata)
            .on("state_changed" , 
            message(`Se ha añadido un nuevo Certificado Digital: ${filename}`, "success")
        );
    }
    const storageDoc = async (url, doc, filename) => {
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
                <UserContext.Consumer>
                    {user =>{
                        if(user !== null){
                            return(
                                <div className="form-row text-center w-full form-fields">
                                    <select className='form-select mb-5 block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding bg-no-repeat border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none' name="id_cert" key="id_cert" onChange={handleCreatedCert} > 
                                        <option key='Seleccione un certificado' value="Seleccione un certificado">Seleccione un certificado</option>
                                        {certs.map( (cert, index) => {
                                            counts[cert.Signers] = (counts[cert.Signers] || 0) + 1; 
                                            return(
                                                <option key={index} value={cert.Filename}>{counts[cert.Signers] > 1? cert.Signers+" "+index : cert.Signers }</option>
                                            )
                                        })}
                                    </select>
                                </div>
                            )
                        }else if(user === null){
                            return(
                                <input type="file" id='file-selector' className='box-border p-3 w-2/3 leading-6 text-justify' onChange={handleUploadedCert} accept=".pfx"/>
                            )
                        }
                    }}
                </UserContext.Consumer>
            </div> 
            <div className="input-div">
                <label htmlFor="">Inserte la clave del Certificado</label>
                <PassInput handlePass={setCertPass}/>
            </div>
            <div className="relative">
                <button onClick={() => goTo('/documents')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >Regresar</button>
                <button className='btn-primary absolute right-0 top-0' onClick={signDoc} disabled={file===null || !cert || !certPass}>Firmar Documento</button>
            </div>
        </>
    )
}
export default UploadFilesForm;

