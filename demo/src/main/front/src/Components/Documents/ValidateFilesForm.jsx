import React, {useState} from 'react';
import { firestore } from '../../Config/config';
import { useNavigate } from 'react-router-dom'
import Searchbar from '../Partials/Searchbar';
import axios from '../../axios.js';
const UploadFilesForm = ({ message, setDocs }) => {
    
    const [file , setFile] = useState([]);
    const [CSV , setCSV] = useState();
    
    // const [docs , setDocs] = useState([]);
    const [openTab, setOpenTab] = useState(1);
    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const verifyCSV = async (CSV, type) =>{
        CSV = CSV.replace(/ /g,"");
        await firestore.collection('documents').where("SignedFile.CSV", '==', CSV).get()
        .then(async (querySnapshot) => {
            const matchedDocs = querySnapshot.size;
            const docs = [];
            querySnapshot.forEach((doc, index) => {
                docs.push({...doc.data(), id: doc.id}); 
            })
            setDocs(null);
            setDocs(docs);
            if (matchedDocs) {
                if(type !== "fileValidate"){
                    await message(`El CSV ingresado es válido para el documento ${docs[0].OriginalFile.Filename}!`, "success");
                }
            } else {
                await message("El CSV ingresado no es válido o no se ha encontrado un documento con ese CSV", "error");
            }
        })
    }

    const verifyDoc = async () =>{

        if(file == null)
        return;
        
        const filesData = new FormData();
        filesData.append("file", file);
        await axios.post(`/verifyDoc`, filesData, {
            headers: {
                Accept: "application/json ,text/plain, */*"
            }
        })
        .then(async res => {
            await setCSV(res.data.CSV);
            if(res.data.Signed){
                await message(`El documento ingresado es válido!`, "success");
                // get CSV from signature
                await verifyCSV(res.data.CSV, "fileValidate");
            }else{
                await message(`El documento ingresado no está firmado!`, "error");
            }
            },(error) => { 
                console.error(error); 
            })
    }

    return (
        <>
          <div className="flex w-1/2">
            <div className="w-full">
              <ul className="tab-list" role="tablist">
                <li className="tab-list-item">
                    <a className={"text-xs font-bold uppercase px-5 py-3 shadow-lg block leading-normal rounded-t-lg " +
                        (openTab === 1
                        ? "text-active-item bg-white"
                        : "text-white bg-active-item")
        } onClick={e => { e.preventDefault(); setOpenTab(1); setDocs(null);}} data-toggle="tab" href="#link1" role="tablist">
                    Validar Documento por archivo</a>
                </li>
                <li className="tab-list-item">
                    <a className={ "text-xs font-bold uppercase px-5 py-3 shadow-lg block leading-normal rounded-t-lg " +
                        (openTab === 2
                            ? "text-active-item bg-white"
                            : "text-white bg-active-item")
                    } onClick={e => {e.preventDefault(); setOpenTab(2); setDocs(null);}} data-toggle="tab" href="#link2" role="tablist">Validar Documento por CSV</a>    
                </li>
              </ul>
              {/* <div className="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded"> */}
                {/* <div className="px-4 py-5 flex-auto"> */}
                  <div className="tab-content">
                    <div className={openTab === 1 ? "block" : "hidden"} id="link1">
                        <div className="w-full flex-nowrap border-none">
                            <label htmlFor="">Seleccione el Documento firmado</label>
                            <input type="file" id='file-selector' className='box-border p-3 w-2/3 leading-6 text-justify' onChange={(e)=> setFile(e.target.files[0])} accept=".pdf"/>
                        </div>
                        <div className="relative">
                            <button onClick={() => goTo('/documents')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >Regresar</button>
                            <button className='btn-primary absolute right-0 top-0' onClick={verifyDoc} >Validar Documento</button>
                        </div>
                    </div>
                    <div className={openTab === 2 ? "block" : "hidden"} id="link2">
                        <div className="w-full flex-nowrap border-none">
                            <label htmlFor="">Ingrese el CSV del documento a Validar</label>
                            {/* <input type="text" id='CSV' className='input-text' placeholder='CSV'/> */}
                            <Searchbar setCSV={setCSV} type="validate"/>
                        </div>
                        <div className="relative">
                            <button onClick={() => goTo('/documents')} className='leading-6 text-center cursor-pointer rounded-md p-2 border bg-gray-200 hover:bg-gray-300' >Regresar</button>
                            <button className='btn-primary absolute right-0 top-0' onClick={()=>verifyCSV(CSV)}>Validar CSV</button>
                        </div>
                    </div>
                  </div>
                {/* </div> */}
              {/* </div> */}
            </div>
          </div>
        </>
      );
    };

export default UploadFilesForm;