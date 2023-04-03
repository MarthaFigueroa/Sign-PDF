import React from 'react'
import { useNavigate } from 'react-router-dom'
import UploadFilesForm from './UploadFileForm'
import { toast } from 'react-toastify';

function UploadFile({type}) {

    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const message = async (msg, type) =>{
        if(type === "success") goTo('/documents');
        toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    return (
        <>
            <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Firmar un Documento</div>
            <div className="flex p-2">
                <div id='signingFile' className='w-full'>
                    <UploadFilesForm message={message} type={type}/>
                </div>
            </div>

        </>
    )
  
}

export default UploadFile