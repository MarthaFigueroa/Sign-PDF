import React from 'react'
import { useNavigate } from 'react-router-dom'
import UploadsForm from './UploadForm'
import { toast } from 'react-toastify';

function Upload({type}) {

    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const createdFile = async (linkName) =>{
        console.log(linkName);
        if(type === 'doc'){
            await goTo('/documents');
            await toast(`Se ha firmado un nuevo documento: ${linkName}`, {
                type: 'success',
                autoClose: 2000
            });
        }else if(type === 'cert'){
            await goTo('/certificates');
            await toast(`Se ha añadido un nuevo Certificado Digital: ${linkName}`, {
                type: 'success',
                autoClose: 2000
            });
        }
    }

    const errorFile = async error =>{
        console.log(error);
        toast(error, {
            type: 'warning',
            autoClose: 2000
        });
    }

    return (
        <>
            <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Cargar un Nuevo {type ==='doc'? 'Documento': type === 'cert'? 'Certificado': null}</div>
            <div className="flex p-2">
                <div className='w-full'>
                    <UploadFilesForm errorFile={errorFile} createdFile={createdFile} type={type}/>
                </div>
            </div>
        </>
    )
  
}

export default Upload