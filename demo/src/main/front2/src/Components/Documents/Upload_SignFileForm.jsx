import React, {useState} from 'react';
// import { storage } from '../firebaseConfig';
import axios from '../../axios.js';
const Upload_SignFileForm = (props) => {
    
    const [file , setFile] = useState([]);
    // const [signedFile , setSignedFile] = useState('');
    const [cert , setCert] = useState([]);
    const [certPass , setCertPass] = useState([]);

    const handlePass = (e)=>{
        let certPassValue = document.getElementById('cert-pass-selector').value;
        setCertPass(certPassValue);        
    }

    const upload = async ()=>{
        if(file == null)
        return;

        const metadata = {
            name: file.name,
            lastModified: file.lastModified,
            size: file.size,
            type: file.type
        }

        console.log("file", file);
        console.log("cert", cert);

        // const form = {
        //     file: file,
        //     cert: cert,
        //     data: new Blob([JSON.stringify({
        //         "certPass": certPass,
        //         "metadata": JSON.stringify(metadata)
        //     })])
        // }


        const filesData = new FormData();
        // filesData.append("file", "jejejeje");
        filesData.append("file", file);
        filesData.append("cert", cert);
        filesData.append('data', new Blob([JSON.stringify({
            "certPass": certPass,
            "metadata": JSON.stringify(metadata)
        })], {
            type: "application/json"
        }));
        
        console.log(Object.fromEntries(filesData));     
        console.log(filesData);
        
        // let formData = [];
        // for (var p of filesData) {
        //     console.log(p);
        //     formData.push({...p});
        // }
        // console.log(formData);
              
        await axios.post(`/sign`, filesData, {
            //headers: {
             //   Accept: "application/json ,text/plain, */*"
            //},
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        })
        .then(async res => {
            console.log("Response Data",res.data);
            console.log("k",res.data.Signed_file);
            const signed = res.data.Signed_file;
            console.log(signed);

            await axios.get(`/file/${signed}`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                },
                responseType:"blob"
            })
            .then(async res => {
                console.log("Response Data File",res.data);
                const signedDoc = new Blob([res.data]);
                // await setSignedFile(signedDoc);
                const myFile = new File([new Blob([res.data])], `Signed_${file.name}`, {
                    type: "application/pdf",
                });
                console.log("Last Modified", myFile.lastModified);
                  
                console.log(myFile);
                //documento
                const url = window.URL.createObjectURL(new Blob([res.data]));
                
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', signed); //or any other extension
                document.body.appendChild(link);
                link.click();

                let formDataSigned = new FormData();
                formDataSigned.append("file", signedDoc);
                formDataSigned.append('data', new Blob([JSON.stringify({
                    "filename": signed
                })], {
                    type: "application/json"
                }));

                await console.log(formDataSigned);

                await axios.post('/uploadSigned', formDataSigned, {
                    headers: {
                        Accept: "application/json ,text/plain, */*"
                    },
                });
            })




        //     // const greetingContent = preval`
        //     //     const fs = require('fs')
        //     //     module.exports = fs.readFileSync(require.resolve(${res.data.Signed_file}), 'utf8')
        //     // `
        //     // console.log(greetingContent);
        //     // var contents = fs.readFileSync(res.data.Signed_file).toString()
        //     // var blob = new Blob([contents], { type: 'application/pdf' });
        //     // var signedFile = new File([blob], res.data.Signed_file, {type: "application/pdf"});
            
            if(res.data.Error){
                if(res.data.Error.message === "The specified network password is not correct."){
                    // alert("El código del Certificado es incorrecto");
                    await props.errorFile("La clave del Certificado es incorrecta", "error");
                }
            }else{
                // await uploadDoc(`/originalDocuments/${file.name}`, file, file.name);
                // await uploadDoc(`/signedDocuments/Signed_${file.name}`, signedFile, `Signed_${file.name}` );
            }

        },(error) => { 
            if (error.response.status === 401) {
                console.log('unauthorized, logging out ...');
                // auth.logout();
                // router.replace('/auth/login');
            }else if (error.response.status === 500) {
                console.log('Request Failed by Internal Server Error...');
                alert('Request Failed by Internal Server Error...');
            }
            console.log("F:",error) 
        })

    }
    
    // const uploadDoc = async (url, doc, filename) => {
    //     const storageRef = ref(storage, url);
    //     // 'file' comes from the Blob or File API
    //     uploadBytes(storageRef, doc).then((snapshot) => {
    //     console.log('Uploaded a blob or file named!', filename);
    //     });
    //     // Create storage ref & put the file in it
    //     // const docRef = storage.ref(url);
    //     //     await docRef.put(doc);
    //         //     .on("state_changed" , 
    //         //     console.log(`success uploading ${filename}`), 
    //         //         props.createdFile(filename)
    //         // );
    
    
    //         // const imgURL = await docRef.getDownloadURL();
    //         // await console.log(`Doc URL: ${imgURL}`);
    //         // await db.collection("users").doc(currentUser.uid).set(
    //         // {
    //         //     dp_URL: imgURL,
    //         //     dp_URL_last_modified: file.lastModifiedDate,
    //         // },
    //         // {
    //         //     merge: true,
    //         // }
    //         // );
    
    //         // await props.createdFile(filename);
    // }
      

    return(
        <>            
         {/*  onSubmit={handleSubmit} */}
            {/* <form> */}
                <div className="form-group input-group formField">
                    <label htmlFor="">Select a Document to Sign</label>
                    <input type="file" id='file-selector' onChange={(e)=>{setFile(e.target.files[0])}} accept=".pdf"/>
                    <label htmlFor="">Select a Certificate to Sign</label>
                    <input type="file" id='cert-selector' onChange={(e)=>{setCert(e.target.files[0])}} accept=".pfx, .p12, .cer, crt, .p7b, .sst"/>
                    <input type="password" id='cert-pass-selector' onChange={handlePass} placeholder="Certificates password"/>
                    <button className='btn btn-info' onClick={upload} disabled={!file || !cert || !certPass}>Upload</button>
                </div>
            {/* </form> */}
        </>
    )
}

export default Upload_SignFileForm;