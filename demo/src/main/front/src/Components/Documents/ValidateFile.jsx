import React, { useState } from 'react'
// import { useNavigate } from 'react-router-dom'
import ValidateFilesForm from './ValidateFilesForm'
import { toast } from 'react-toastify';
import Card from '../Partials/Card';

function ValidateFile({type}) {

    // const navigate = useNavigate();
    const [docs , setDocs] = useState([]);

    // const goTo = (route) =>{
    //     console.log("kkk");
    //     navigate(route);
    // }
    const message = async (msg, type) =>{
        await toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    return (
        <>
            {/* <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Subir un Nuevo Documento</div>
            <div className="lg:flex lg:flex-wrap"> */}
                {/* flex p-2 */}
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
                {/* </div> */}
            {/* </div> */}
        </>
    )
  
}

export default ValidateFile