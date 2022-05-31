import React, {useState} from 'react';
import { firestore, storage } from '../../Config/config';
// import { Formik, Form, Field, ErrorMessage } from 'formik';
import { useNavigate } from 'react-router-dom'
import * as Icons from "react-icons/ai";
import axios from '../../axios.js';
import PassInput from '../Partials/PassInput';
const UploadFilesForm = (props) => {
    
    // const [docs , setDocs] = useState([]);
    const [cert , setCert] = useState([]);
    const [existingCert , setExistingCert] = useState(false);
    const navigate = useNavigate();
    const [certPass , setCertPass] = useState([]);

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    const handlePass = (certPassValue)=>{
        setCertPass(certPassValue);        
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
                await props.message("El certificado con el que desea firmar ya existe", "warning");
                setExistingCert(true);
                setCert(null);
            } else {
                console.log("0 certificates matched the query");
                setExistingCert(false);
                setCert(e.target.files[0]);
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
        const metadata = {
            contentType: 'application/x-pkcs12',
            size: doc.size
        };
          
        // Create storage ref & put the file in it
        storage.ref(url).put(doc, metadata)
            .on("state_changed" , 
            console.log(`success uploading ${filename}`),
                // console.log(`${filename} Uploaded`)
            props.message(`Se ha añadido un nuevo Certificado Digital: ${filename}`, "success")
        );
    }

    const uploadCert = async ()=>{
        if(cert === null)
        return;

        console.log(cert);

        const certMetadata = {
            name: String(cert.name),
            lastModified: cert.lastModified,
            size: cert.size,
            type: String(cert.type), 
            certPass: String(certPass)
        }

        console.log(certMetadata);

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
            console.log("Response Data",res.data);
            const data = res.data;
            if(data.Error){
                if(data.Error.message === "The specified network password is not correct."){
                    await props.message("La clave del Certificado es incorrecta", "error");
                }
            }else if(data.Signers !== null){
                firestore.collection('certificates').where("Filename", '==', data.Filename && "Signers", '==', data.Signers).get()
                .then(async (querySnapshot) => {
                    const matchedDocs = querySnapshot.size;
                    console.log(matchedDocs);
                    if (matchedDocs === 0) {
                        await axios.post(`/createCertificate`, data, {
                            headers: {
                                Accept: "application/json ,text/plain, */*"
                            }
                          })
                          .then(async res => {
                            console.log(res.data);
                          })
                        storage.ref().child('/certificates').listAll()
                        .then(async res => {
                            const certNames = res.items.filter(item => item.name === data.Filename);
                            if(certNames.length === 0){
                                await uploadDoc(`/certificates/${cert.name}`, cert, cert.name);
                            }
                            goTo('/certificates');
                            props.message(`Se ha añadido un nuevo Certificado Digital: ${data.Filename}`, "success")
                        })                        
                        .catch(err => {
                            alert(err.message);
                        })
                    }
                })
            }else{
                await props.message("La clave del Certificado es incorrecta", "error");
            }
        })
    }
    
    return(
        <>
            <div className="relative items-stretch w-full flex-nowrap border-none">
                <label htmlFor="">Seleccione un certificado para firmar el documento</label>
                <input type="file" className='box-border p-3 w-2/3 leading-6 text-justify' id='cert-selector' onChange={handleUploadedCert} accept=".pfx"/>
                {/* , crt, .p7b, .sst, .p12, .cer */}
                {existingCert ? 
                    <span className='text-yellow-500 inline-flex text-xl'>
                        <Icons.AiFillWarning />
                    </span>
                    : null
                }
            </div>
            <div className="input-div">
                    <label htmlFor="">Inserte la clave del Certificado</label>
                    <PassInput handlePass={handlePass}/>
                </div>
            <div className="relative mt-2">
                    <button onClick={() => goTo('/certificates')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >cancel</button>
                    <button className='btn-primary absolute right-0 top-0' onClick={uploadCert} disabled={ !cert }>Upload</button>
            </div>

        </>
    )
}

export default UploadFilesForm;