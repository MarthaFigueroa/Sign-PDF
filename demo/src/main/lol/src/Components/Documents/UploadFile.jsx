import React from 'react'
import { useNavigate } from 'react-router-dom'
import UploadFilesForm from './UploadFileForm'
import { toast } from 'react-toastify';

function UploadFile({type}) {

    const navigate = useNavigate();

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    // const createdFile = async (linkName) =>{
    //     console.log(linkName);
    //     await goTo('/documents');
    //     await toast(`Se ha firmado un nuevo documento: ${linkName}`, {
    //         type: 'success',
    //         autoClose: 2000
    //     });
    // }

    const message = async (msg, type) =>{
        type === "success" ? goTo('/documents') : console.log("Alert");
        console.log(msg);
        await toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    return (
        <>
            <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Cargar un Nuevo Documento</div>
            <div className="flex p-2">
                <div className='w-full'>
                    <UploadFilesForm message={message} type={type}/>
                </div>
            </div>
        </>
    )
  
}

export default UploadFile