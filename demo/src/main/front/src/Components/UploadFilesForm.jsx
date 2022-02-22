import React, {useState} from 'react';
import axios from 'axios';
const UploadFilesForm = (props) => {

    const [image , setImage] = useState('');

    // let metadata = {
    //     name: image.name !== undefined ? image.name : "",
    //     contentType: image.type !==undefined? image.type : "",
    //     lastModified: image.lastModified !==undefined? image.lastModified : "",
    //     size: image.size !==undefined? image.size : ""
    // };
    // const [values, setValues] = useState(metadata);

    // const handleSubmit = async(e) =>{
    //     e.preventDefault();
    //     setValues({...metadata});
    //     await props.notify(values);
    // }
    
    // useEffect(() => {
    //     const fileSelector = document.getElementById('file-selector');
    //     fileSelector.addEventListener('change', (event) => {
    //         const fileList = event.target.files;
    //         console.log(fileList);
    //     });
    // })
    
    const upload = ()=>{
        if(image == null)
        return;
        console.log("Image: ",image);
        const req = {
            filename: image.name,
            metadata: image
        }
        console.log("Req: ",image);
        axios.post(`http://localhost:8080/file`, req)
        .then(async res => {
            console.log("Response Data",res.data);
            // setValues({...metadata});
            // await props.notify(values);
        })
        // firebase.ref(`/documents/${image.name}`).put(image)
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
         {/* onSubmit={handleSubmit} */}
            {/* <form > */}
                <div className="form-group input-group formField">
                    <input type="file" id='file-selector' onChange={(e) => { setImage(e.target.files[0])}} accept=".pdf"/>
                    <button className='btn btn-info' onClick={upload}>Upload</button>
                </div>
            {/* </form> */}
        </>
    )
}

export default UploadFilesForm;