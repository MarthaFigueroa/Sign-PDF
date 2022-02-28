import React, {useState} from 'react';
// import CryptoJS from 'crypto-js';
import axios from '../axios.js';
const UploadFilesForm = (props) => {

    const [file , setFile] = useState('');
    const [cert , setCert] = useState('');
    const [certPass , setCertPass] = useState('');

    // useEffect(() => {
    //     const fileSelector = document.getElementById('file-selector');
    //     fileSelector.addEventListener('change', (event) => {
    //         const fileList = event.target.files;
    //         console.log(fileList);
    //     });
    // })
    const handlePass = ()=>{
        let certPassValue = document.getElementById('cert-pass-selector').value;
        // certPassValue = CryptoJS.AES.encrypt(certPass, 'validacionesKey').toString();
        // certPassValue = bcrypt.hashSync(certPassValue, '$2a$10$CwTycUXWue0Thq9StjUM0u');
        setCertPass(certPassValue);
    }

    const upload = ()=>{
        if(file == null)
        return;
        console.log("file: ",file);

        let formData = new FormData();
        formData.append("file", file);
        formData.append("cert", cert);
        
        const req = {
            formData: formData,
            certPass: certPass
        }
        console.log("Req: ",req);
        // axios.post(`http://localhost:8080/file`, req)
        // .then(async res => {
        //     console.log("Response Data",res.data);
        //     // setValues({...metadata});
        //     await props.notify(file);
        // })

        axios.post(`/upload`, formData, {
            headers: {
                // "Content-Type": "multipart/form-data",
                Accept: "application/json ,text/plain, */*"
            },
        })
        .then(async res => {
            console.log("Response Data",res.data);
            // setValues({...metadata});
            await props.notify(file);
        })

        // firebase.ref(`/documents/${file.name}`).put(file)
        // .on("state_changed" , function(snapshot){
        //     var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        //     console.log('Upload is ' + progress + '% done');
        //     switch (snapshot.state) {
        //         case firebase.storage.TaskState.PAUSED: // or 'paused'
        //         console.log('Upload is paused');
        //         break;
        //         case firebase.storage.TaskState.RUNNING: // or 'running'
        //         console.log('Upload is running');
        //         break;
        //         default:
        //         console.log("Waiting");
        //     }
        // } , alert);
    }
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

export default UploadFilesForm;