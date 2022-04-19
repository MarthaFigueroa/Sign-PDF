import React, {useEffect, useState} from 'react';
import { firestore, storage } from '../../firebaseConfig';
// import { Formik, Form, Field, ErrorMessage } from 'formik';
import { useNavigate } from 'react-router-dom'
import * as Icon from "@material-ui/icons";
import axios from '../../axios.js';
// import SelectOption from '../ExtraComponents/SelectOption.jsx';
import PassInput from '../ExtraComponents/PassInput';
// import CardStyles from '../../CardStyles';
// import * as Icon from "@material-ui/icons";
// import CardDiv from '../ExtraComponents/CardDiv';
// import UploadCert from '../Certificates/UploadCert';
const UploadForm = (props) => {
    
    // const [docs , setDocs] = useState([]);
    const [file , setFile] = useState('');
    const [cert , setCert] = useState('');
    const [certs , setCerts] = useState([]);
    const [existingCert , setExistingCert] = useState(false);
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

    const handleCertOptions = (e) =>{
        setCertOption(e.target.value);
    }

    const handleCreatedCert = async (e) =>{
        const filename = e.target.value;
        console.log(filename);
        // console.log(certs);

        if(filename !== "Seleccione un certificado"){
            const certName = certs.filter(cert => cert.Filename === filename);
            const certType = certName[0].type;
            console.log(certType);
            await axios.get(`/file/${filename}`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                },
                responseType:"blob"
            })
            .then(async res => {
                console.log("GG:",res.data);
                const certFile = new Blob([res.data], {type : certType});
                // const data = new Blob([JSON.stringify({
                //     "filename": filename
                // })], {
                //     type: certType
                // })

                // const certFile = new File([res.data], {type : certType}, filename);
                // let formDataSigned = new FormData();
                // formDataSigned.append("file", certFile);
                // console.log(formDataSigned);
                certFile.lastModified = parseInt(certName[0].LastModified);
                certFile.name = certName[0].Filename;
                console.log(certFile);
                setCert(certFile);
            })
            // setCerts(e.target.files[0]);
        }else{
            setCert(null);
        }
    }

    const handleUploadedCert = (e) =>{
        console.log(e.target.files[0]);
        firestore.collection('certificates').where("Filename", '==', e.target.files[0].name).get()
        .then(async (querySnapshot) => {
            // total matched documents
            const matchedDocs = querySnapshot.size
            if (matchedDocs) {
                // querySnapshot.docs.forEach(async doc => {
                //     console.log(doc.id, "=>", doc.data())
                // })
                await props.errorFile("El certificado con el que desea firmar ya existe", "warning");
                setExistingCert(true);
            } else {
                console.log("0 certificates matched the query");
                setExistingCert(false);
                setCert(e.target.files[0]);
            }
        })
        
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

            if(!res.data.preSigned){
                console.log("Response File Data",res.data.FileData);
    
                const OriginalFile = {
                    "Filename": res.data.FileData.Filename,
                    "File_hash": res.data.FileData.File_hash,
                    "File": res.data.FileData.File,
                    "LastModified": res.data.FileData.LastModified_originalFile,
                    "Size": res.data.FileData.Size_originalFile,
                    "Type": res.data.FileData.Type_originalFile
                }
    
                const SignedFile = {
                    "Filename": res.data.FileData.Signed_filename,
                    "CSV": res.data.FileData.CSV,
                    "Signers": res.data.FileData.Signers,
                    "File_hash": res.data.FileData.Signed_file_hash,
                    "File": res.data.FileData.Signed_file,
                    "LastModified": res.data.FileData.LastModified_signedFile,
                    "Size": res.data.FileData.Size_signedFile,
                    "Type": res.data.FileData.Type_signedFile,
                }
    
                const docData = {
                    OriginalFile,
                    SignedFile
                }
    
                console.log(res.data.CertData);
    
                firestore.collection('certificates').where("Filename", '==', res.data.CertData.Filename).get()
                .then(async (querySnapshot) => {
                    // total matched documents
                    const matchedDocs = querySnapshot.size
                    if (!matchedDocs) {
                        await firestore.collection('certificates').add(res.data.CertData);
                    }
                })
                await firestore.collection('documents').add(docData);
                const signed = res.data.FileData.Signed_filename;
                
                convertToBlob(signed);
    
                if(res.data.Error){
                    if(res.data.Error.message === "The specified network password is not correct."){
                        await props.errorFile("La clave del Certificado es incorrecta", "error");
                    }
                }else{
                    await uploadDoc(`/originalDocuments/${file.name}`, file, file.name);
                    const arr = [];
                    storage.ref().child('certificates/').listAll()
                    .then(async res => {
                        res.items.forEach((item) => {
                        // setData(arr => [...arr, item.name]);
                            arr.push(item.name);
                        })
                        if(arr.length === 0){
                            await uploadDoc(`/certificates/${cert.name}`, cert, cert.name);
                        }
                    })
                    .catch(err => {
                        alert(err.message);
                    })
                    console.log(arr);
                }
            }else{
                props.errorFile("El documento que ha intentado firmar ya está firmado", "warning");
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
                    await props.errorFile("El documento que ha intentado firmar ya existe", "warning");
                })
            } else {
                console.log("0 documents matched the query")
                const metadata = {
                    name: String(file.name),
                    lastModified: String(file.lastModified),
                    size: file.size,
                    type: String(file.type)
                }

                const certMetadata = {
                    name: String(cert.name),
                    lastModified: String(cert.lastModified),
                    size: cert.size,
                    type: String(cert.type),
                    certPass: String(certPass)
                }
        
                console.log("Cert:",cert);
                const filesData = new FormData();
                filesData.append("file", file);
                filesData.append("cert", cert);
                filesData.append('data', new Blob([JSON.stringify({
                    // "certPass": certPass,
                    // "certLast": certLast,
                    // "docLast": docLast,
                    "docMetadata": metadata,
                    "certMetadata": certMetadata,
                    // "certName": cert.name,
                    // "metadata": JSON.stringify(metadata)
                })], {
                    type: "application/json"
                }));
                
                if(cert !== null){
                    signDoc(filesData);
                }
            }
        })
    }

    const uploadCert = async ()=>{
        if(cert === null)
        return;

        console.log(cert);

        const certMetadata = {
            name: String(cert.name),
            lastModified: String(cert.lastModified),
            size: cert.size,
            type: String(cert.type)
        }

        await axios.post(`/certs`, certMetadata, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(async res => {
            console.log("Response Data",res.data);
            firestore.collection('certificates').where("Filename", '==', res.data.Filename && "Signers", '==', res.data.Signers).get()
            .then(async (querySnapshot) => {
                // total matched documents
                const matchedDocs = querySnapshot.size
                if (!matchedDocs) {
                    await firestore.collection('certificates').add(res.data);
                    const arr = [];
                    storage.ref().child('certificates/').listAll()
                    .then(async res => {
                        res.items.forEach((item) => {
                        // setData(arr => [...arr, item.name]);
                            arr.push(item.name);
                        })
                        if(arr.length === 0){
                            await uploadDoc(`/certificates/${cert.name}`, cert, cert.name);
                        }
                    })
                    .catch(err => {
                        alert(err.message);
                    })
                }
            })
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

            // url === `/certificates/${filename}`? null:
            props.createdFile(filename)
        );
    }

    useEffect(() => {
        if(props.type === 'doc'){
            const certsArr = []
            firestore.collection('certificates').onSnapshot(async snapshot => {
                snapshot.forEach(async (doc, index) => {
                //   console.log(doc.data());
                  certsArr.push({...doc.data(), id: doc.id, index: index}); 
                });
                // console.log(certsArr);
                await setCerts(certsArr);
            })            
        }
    })
    
    return(
        <>            
         {/*  onSubmit={handleSubmit} */}
            {/* <form> */}
                {/* <div className="col-md-6"> */}
                {(()=>{
                    if(props.type === 'doc'){
                        return (
                            <>
                            <div className="relative items-stretch w-full flex-nowrap border-none">
                                <label htmlFor="">Seleccione el a Documento a Firmar</label>
                                <input type="file" id='file-selector' className='box-border p-3 w-full leading-6 text-justify' onChange={(e)=>{setFile(e.target.files[0])}} accept=".pdf"/>
                            </div>

                            {/* <div className="relative flex flex-wrap items-stretch w-full"> */}
                            <div className="relative items-stretch w-full flex-nowrap border-none">
                                <label htmlFor="">Seleccione una de las opciones de Certificado</label>
                                <div className="form-row text-center w-2/3 form-fields">
                                    <select className='form-select mb-5 block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding bg-no-repeat border border-solid border-gray-300 rounded transition ease-in-out m-0      focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none' id='certification-select' name="id_cert" key="id_cert" onChange={handleCertOptions} > 
                                        {
                                            certOptions.map( (certOption) => (
                                                <option key={certOption.id} value={certOption.id}>{certOption.name}</option>
                                            ))
                                        }
                                    </select>
                                </div>
                            </div>
                                {/* <select>{dateOptions.map((Option, index) => <option key={`unique_${index}`}><Option /></option>)}</select> */}

                                {/* <SelectOption /> */}

                                {(() => {
                                    if (certOption === "CreatedCert") {
                                        return (
                                            <div className="relative items-stretch w-full flex-nowrap border-none">
                                                <label htmlFor="">Seleccione un certificado para firmar el documento</label>
                                                <div className="form-row text-center w-full form-fields">
                                                    <select className='form-select mb-5 block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding bg-no-repeat border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none' name="id_cert" key="id_cert" onChange={handleCreatedCert} > 
                                                        <option key='Seleccione un certificado' value="Seleccione un certificado">Seleccione un certificado</option>
                                                        {
                                                            certs.map( (cert, index) => (
                                                                <option key={index} value={cert.Filename}>{cert.Signers}</option>
                                                            ))
                                                        }
                                                    </select>
                                                </div>
                                            </div>
                                        )
                                    } else if (certOption === "UploadCert") {
                                        return (
                                            <div className="relative items-stretch w-full flex-nowrap border-none">
                                                <label htmlFor="">Seleccione un certificado para firmar el documento</label>
                                                <input type="file" className='box-border p-3 w-2/3 leading-6 text-justify' id='cert-selector' onChange={handleUploadedCert} accept=".pfx, .p12, .cer"/>
                                                {/* , crt, .p7b, .sst */}
                                                {existingCert ? <span className='text-yellow-500'>
                                                            <Icon.Warning />
                                                        </span>: null
                                                }
                                            </div>
                                            // (e)=>{setCert(e.target.files[0])}
                                        )
                                    }
                                })()}

                                {/* <input type="password" id='cert-pass-selector' onChange={() => handlePass("cert-pass-selector")} placeholder="Clave del Certificado"/> */}
                                <div className="input-div">
                                    <label htmlFor="">Inserte la clave del Certificado</label>
                                    {/* <CertKey handlePass={handlePass} /> */}
                                    <PassInput handlePass={handlePass}/>
                                </div>
                                <div className="relative">
                                {/* <NavLink to={"/documents"} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</NavLink> */}
                                    <button onClick={() => goTo('/documents')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</button>
                                    <button className='btn-primary absolute right-0 top-0' onClick={upload} disabled={!file || !cert || !certPass}>Upload</button>
                                </div>
                            </>
                        )
                    }else if(props.type === 'cert'){
                        return (
                            <>
                                <div className="relative items-stretch w-full flex-nowrap border-none">
                                    <label htmlFor="">Seleccione un certificado para firmar el documento</label>
                                    <input type="file" className='box-border p-3 w-2/3 leading-6 text-justify' id='cert-selector' onChange={handleUploadedCert} accept=".pfx"/>
                                    {/* , crt, .p7b, .sst, .p12, .cer */}
                                    {existingCert ? <span className='text-yellow-500'>
                                                <Icon.Warning />
                                            </span>: null
                                    }
                                </div>
                                <div className="relative mt-2">
                                    {/* <NavLink to={"/documents"} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</NavLink> */}
                                        <button onClick={() => goTo('/certificates')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</button>
                                        <button className='btn-primary absolute right-0 top-0' onClick={uploadCert} disabled={!file || !cert || !certPass}>Upload</button>
                                </div>
                            </>
                        )
                    }
                })()}
                {/* </div> */}
                {/* {certPass} */}

                {/* </div> */}

            {/* </form> */}
        </>
    )
}

export default UploadForm;