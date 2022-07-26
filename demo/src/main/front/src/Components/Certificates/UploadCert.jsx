import React from 'react'
import { useNavigate } from 'react-router-dom'
import UploadCertsForm from './UploadCertForm'
import { toast } from 'react-toastify';

function UploadCert() {

    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const message = async (msg, type) =>{
        if(type === "success") goTo('/certificates');
        toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    return (
        <>
            <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Subir un Nuevo Certificado Digital</div>
            <div className="flex p-2">
                <div id='signingFile' className='w-full'>
                    <UploadCertsForm message={message}/>
                </div>
            </div>
        </>
    )
  
}

export default UploadCert