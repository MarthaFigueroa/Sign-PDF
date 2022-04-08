import React from 'react'
import { useNavigate } from 'react-router-dom'
import UploadFilesForm from './UploadFileForm'
import { toast } from 'react-toastify';

function UploadFile() {

    const navigate = useNavigate();

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    const createdFile = async (linkName) =>{
        await goTo('/documents');
        await toast(`New File Uploaded: ${linkName}`, {
            type: 'success',
            autoClose: 2000
        });
    }

    const errorFile = async error =>{
        console.log(error);
        toast(error, {
            type: 'error',
            autoClose: 2000
        });
    }

    return (
        <div>
            <UploadFilesForm errorFile={errorFile} createdFile={createdFile}/>
        </div>
    )
  
}

export default UploadFile