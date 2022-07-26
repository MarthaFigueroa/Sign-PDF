import React, { useState } from 'react';
import ValidateFilesForm from './ValidateFilesForm'
import { toast } from 'react-toastify';
import Card from '../Partials/Card';

function ValidateFile({type}) {

    const [docs , setDocs] = useState([]);

    const message = async (msg, type) =>{
        toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    return (
        <>
            <div className='flex justify-center w-full'>
                <ValidateFilesForm message={message} setDocs={setDocs}/>
            </div>
            {(()=>{
                if(docs !== null){
                    return(
                        <div className="flex justify-center w-full">
                            {docs.map((doc, index) =>(
                                <div key={index}>
                                    <li className='list-none' key={index}>
                                        <Card index={index} doc={doc} url={doc.OriginalFile.File} type="validate" />
                                    </li>
                                </div>
                            ))}
                        </div>
                    )
                }
            })()}
        </>
    )
  
}

export default ValidateFile