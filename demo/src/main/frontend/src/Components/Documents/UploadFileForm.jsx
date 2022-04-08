import React, {useState} from 'react';
import { firestore, storage } from '../../firebaseConfig';
// import { Formik, Form, Field, ErrorMessage } from 'formik';
import { useNavigate } from 'react-router-dom'
import axios from '../../axios.js';
// import SelectOption from '../ExtraComponents/SelectOption.jsx';
import CertKey from '../ExtraComponents/CertKey.jsx';
// import CardDiv from '../ExtraComponents/CardDiv';
// import UploadCert from '../Certificates/UploadCert';
const UploadFilesForm = (props) => {
    
    // const [docs , setDocs] = useState([]);
    const [file , setFile] = useState([]);
    const [cert , setCert] = useState([]);
    const [certs , setCerts] = useState([]);
    const [certOption , setCertOption] = useState([]);
    const [certPass , setCertPass] = useState([]);
    const navigate = useNavigate();


    const handlePass = (certPassValue)=>{
        // let certPassValue = document.getElementById(id).value;
        // console.log(certPassValue);
        setCertPass(certPassValue);        
    }

    const certOptions = [
        {
            "id": "Seccione una Opción", 
            "name": "Seccione una Opción"
        }, 
        {
            "id": "CreatedCert", 
            "name": "Created Cert"
        }, 
        {
            "id": "UploadCert",
            "name": "Upload Cert"
        }
    ];

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    const handleCertOption = (e) =>{
        setCertOption(e.target.value);
    }

    const handleCert = (e) =>{
        setCerts(e.target.value);
    }

    const convertToBlob = async (signed) =>{
        await axios.get(`/file/${signed}`, {
            headers: {
                Accept: "application/json ,text/plain, */*"
            },
            responseType:"blob"
        })
        .then(async res => {
            // convertToBlob(res.data, filename);
            const signedDoc = new Blob([res.data]);
            let formDataSigned = new FormData();
            formDataSigned.append("file", signedDoc);
            formDataSigned.append('data', new Blob([JSON.stringify({
                "filename": signed
            })], {
                type: "application/json"
            }));

            await axios.post('/uploadSigned', formDataSigned, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                },
            }).then(async res => {
                console.log("Response Data File",res.data);
                await goTo('/documents');
                await props.createdFile(file.name);
            });
        })
    }

    const signDoc = async (filesData) =>{
        await axios.post(`/sign`, filesData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(async res => {
            console.log("Response Data",res.data);
            console.log("Response File Data",res.data.FileData);

            const OriginalFile = {
                "Filename": res.data.FileData.Filename,
                "File_hash": res.data.FileData.File_hash,
                "File": res.data.FileData.File,
            }

            const SignedFile = {
                "Signed_filename": res.data.FileData.Signed_filename,
                "CSV": res.data.FileData.CSV,
                "Signers": res.data.FileData.Signers,
                "Signed_file_hash": res.data.FileData.Signed_file_hash,
                "Signed_file": res.data.FileData.Signed_file,
            }

            const docData = {
                OriginalFile,
                SignedFile
            }
            await firestore.collection('certificates').add(res.data.CertData);
            await firestore.collection('documents').add(docData);
            const signed = res.data.FileData.Signed_filename;
            
            convertToBlob(signed);

            if(res.data.Error){
                if(res.data.Error.message === "The specified network password is not correct."){
                    await props.errorFile("El código del Certificado es incorrecto");
                }
            }else{
                await uploadDoc(`/originalDocuments/${file.name}`, file, file.name);
                await uploadDoc(`/certificates/${cert.name}`, cert, cert.name);
            }
        },(error) => { 
            console.log("F:",error) 
        })
    }

    const upload = async ()=>{
        if(file == null)
        return;

        firestore.collection('documents').where('OriginalFile.Filename', '==', file.name).get()
        .then(async (querySnapshot) => {
            // total matched documents
            const matchedDocs = querySnapshot.size
            if (matchedDocs) {
            querySnapshot.docs.forEach(async doc => {
                console.log(doc.id, "=>", doc.data())
                await props.errorFile("El documento que ha intentado firmar ya existe");
            })
            } else {
                console.log("0 documents matched the query")
                const metadata = {
                    name: file.name,
                    lastModified: file.lastModified,
                    size: file.size,
                    type: file.type
                }
        
                let certLast = String(cert.lastModified);
        
                const filesData = new FormData();
                filesData.append("file", file);
                filesData.append("cert", cert);
                filesData.append('data', new Blob([JSON.stringify({
                    "certPass": certPass,
                    "certLast": certLast,
                    "metadata": JSON.stringify(metadata),
                })], {
                    type: "application/json"
                }));
                
                signDoc(filesData);
            }
        })
    }
    
    const uploadDoc = async (url, doc, filename) => {
        console.log(url);
        // const storageRef = ref(storage, url);
        // // 'file' comes from the Blob or File API
        // uploadBytes(storageRef, doc).then((snapshot) => {
        //     console.log('Uploaded a blob or file named!', filename);
        // });
        // Create storage ref & put the file in it
        storage.ref(url).put(doc)
            .on("state_changed" , 
            console.log(`success uploading ${filename}`),
                // console.log(`${filename} Uploaded`)

                props.createdFile(filename)
        );
    }

    return(
        <>            
         {/*  onSubmit={handleSubmit} */}
            {/* <form> */}
                {/* <div className="col-md-6"> */}
                <div className="form-group input-group">
                    <label htmlFor="">Seleccione el a Documento a Firmar</label>
                    <input type="file" id='file-selector' onChange={(e)=>{setFile(e.target.files[0])}} accept=".pdf"/>
                    <label htmlFor="">Seleccione una de las opciones de Certificado</label>
                    {/* <select>{dateOptions.map((Option, index) => <option key={`unique_${index}`}><Option /></option>)}</select> */}

                    {/* <SelectOption /> */}
                    <div className="form-row text-center form-fields">
                        <select id='certification-select' name="id_cert" key="id_cert" onChange={handleCertOption} > 
                            {
                                certOptions.map( (certOption) => (
                                    <option key={certOption.id} value={certOption.id}>{certOption.name}</option>
                                ))
                            }
                        </select>
                    </div>

                    {(() => {
                        if (certOption === "CreatedCert") {
                            return (
                                <select name="id_cert" key="id_cert" onChange={handleCert} > 
                                    {
                                        certs.map( (cert) => (
                                            <option key={cert.id} value={cert.id}>{cert.name}</option>
                                        ))
                                    }
                                </select>
                            )
                        } else if (certOption === "UploadCert") {
                            return (
                                <input type="file" id='cert-selector' onChange={(e)=>{setCert(e.target.files[0])}} accept=".pfx, .p12, .cer, crt, .p7b, .sst"/>
                            )
                        }
                    })()}

                    {/* <input type="password" id='cert-pass-selector' onChange={() => handlePass("cert-pass-selector")} placeholder="Clave del Certificado"/> */}
                    <div className='passDiv'>
                        <CertKey handlePass={handlePass} />
                    </div>
                    <button className='btn btn-create' onClick={upload} disabled={!file || !cert || !certPass}>Upload</button>
                </div>
                {/* {certPass} */}

                {/* </div> */}

            {/* </form> */}
        </>
    )
}

export default UploadFilesForm;