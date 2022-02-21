import React from 'react'
import UploadFilesForm from './UploadFilesForm'
import { toast } from 'react-toastify';

function UploadFile() {

    const notify = async (linkObject) =>{
        console.log(linkObject);
        await toast(`New File Uploaded: ${linkObject.name}`, {
            type: 'success',
            autoClose: 2000
        });
    }

    return (
        <div>
            <UploadFilesForm notify={notify}/>
        </div>
    )
  
}

export default UploadFile