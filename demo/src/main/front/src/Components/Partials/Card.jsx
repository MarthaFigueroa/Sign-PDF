import React, { useEffect } from "react";
import * as IconsHi from "react-icons/hi";
import * as IconsAi from "react-icons/ai";
import * as IconsRi from "react-icons/ri";
import * as IconsFa from "react-icons/fa";
import TimeAgo from 'react-timeago';
import { toast } from 'react-toastify';
import image from "./../../public/img/certificado.jpg";
import documentImage from "./../../public/img/documentImage.png";
import { UserContext } from "../../Providers/UserProvider";

const Card = ({ doc, url, onDelete, index, type, onDisableUser, onEnableUser }) => {

  const copy = (id) => {
    const csv = document.getElementById(id);
    var selection = window.getSelection();
    var range = document.createRange();
    range.selectNodeContents(csv);
    selection.removeAllRanges();
    selection.addRange(range);
    document.execCommand("Copy");
    message("¡Se ha copiado el CSV al portapapeles!", "success");
    // alert("Copied div content to clipboard");
  }

  const message = async (msg, type) =>{
    toast(msg, {
        position: "bottom-right",
        type: type,
        autoClose: 2000
    });
}

  useEffect(() => {
    const pdfPreview = (url) =>{
      // Loaded via <script> tag, create shortcut to access PDF.js exports.
      var pdfjsLib = window['pdfjs-dist/build/pdf'];
      pdfjsLib.GlobalWorkerOptions.workerSrc = '//mozilla.github.io/pdf.js/build/pdf.worker.js';
      var loadingTask = pdfjsLib.getDocument(url);
      loadingTask.promise.then(function(pdf) {
        // Fetch the first page
        var pageNumber = 1;
        pdf.getPage(pageNumber).then(function(page) {
          let scale;
          if(type==="validate"){
            scale = 0.6;
          }else{
            scale = 0.535;
          }
          
          var viewport = page.getViewport({scale: scale});
          var div = document.getElementById(doc.id);
          var existingCanvas = document.getElementById(index);
          if(div.contains(existingCanvas)){
            removePreview(index);
          }
            var canvas = document.createElement('canvas');
            canvas.id = index;
            var context = canvas.getContext('2d');
            if(type === "validate"){
              canvas.height = 224;
              canvas.width = 420;
            }else{
              canvas.height = 224;
              canvas.width = 320;
            }
            div.appendChild(canvas);
            var renderContext = {
              canvasContext: context,
              viewport: viewport
            };
            var renderTask = page.render(renderContext);
            renderTask.promise.then(function () {
            });
        });
      }, function (reason) {
        // PDF loading error
        console.error(reason);
        if(reason){
          var div = document.getElementById(doc.id);
          var image = document.createElement("img");
          image.src = documentImage;
          div.appendChild(image);
        }

      });
    }

    const removePreview = (index) =>{
      let canvas = document.getElementById(index);
      canvas.remove();
    }

    if(type === "doc" || type === "validate"){
      pdfPreview(url, doc, index);
      // formatterDate(doc.OriginalFile.Created_at);
    }
  }, [url, doc, index, type])
  
  return (
    <>
      <div className={type==="validate"? "card w-full": type==="user" ? "card mt-36": "card"}>
        {/* Preview */}
          {(()=>{
            if(type === "cert"){
              return(
                <div id={doc.id} className="rounded-t-2xl overflow-hidden relative w-full justify-center flex">
                  <img className="h-auto signersIcon w-full" width={200} src={image} alt="Signers User" />
                </div>
              )
            }else if(type === "doc" || type === "validate"){
              return(
                <>
                  <div id={doc.id} className="h-56 rounded-t-2xl overflow-hidden relative w-full justify-center flex"></div>
                </>
              )              
            }
            else if(type === "user"){
              return(
                <>
                  <div className="mt-0">
                    <div className="flex justify-center absolute top-neg-150 right-0 left-0 ">
                      <img className="signersIcon h-48 w-48 mx-auto mb-12 absolute userImg" referrerPolicy="no-referrer" src={doc.photoURL} alt="👨🏻" />
                    </div>
                  </div>
                </>
              )
            }
          })()}
        {/* Delete */}
          <div className="absolute right-0">
            <UserContext.Consumer>
              {user =>{
                if(user !== null){
                  if(user.role === "admin"){
                    if(type === "doc"){
                      return(
                        <>
                          <button className="drop-shadow-lg" onClick={()=>onDelete(doc.id, doc.OriginalFile.Filename)}>
                            <IconsAi.AiFillCloseCircle className="text-2xl text-slate-400" />
                          </button>
                        </>
                      )
                    }
                  }
                }
              }}
            </UserContext.Consumer>
          </div>
        
          <div className={type==="user"? "mt-20": ""}>
            {/* Time */}
            <div className="flex relative mb-5">
              <div className="flex absolute right-2">
                {(()=>{
                if(type === "cert" || type === "user"){
                  return(
                    <>
                    Created <TimeAgo className="ml-1" date={doc.Created_at}/>
                    </>
                  )
                }else if(type === "doc" || type === "validate"){
                  const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio","Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
                  const now = new Date();
                  const datetime = new Date(doc.OriginalFile.Created_at);
                  const date = ("0"+datetime.getDate()).slice(-2)+"/"+monthNames[datetime.getMonth()]+"/"+datetime.getFullYear()+" ";
                  const time = datetime.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
                  const fullDate = date + time;
                  const threeMinutes = 3*60000;
                  const range = new Date(datetime.getTime() + threeMinutes);
                  if(now > range){
                    return(
                      <>
                        <label htmlFor="">{fullDate}</label>
                      </>
                    )
                  }else{
                    return(
                      <>
                        Created <TimeAgo className="ml-1" date={doc.OriginalFile.Created_at}/>
                      </>
                    )
                  }
                }
              })()}
              </div>
            </div>
          {/* Functions */}
            <div className="relative bottom-0 left-0 -mb-4 mx-auto flex flex-row justify-center pt-2">
              {(() =>{
                if(type === "cert"){
                  return(
                    <>
                      <div className="btn-download mr-2">
                        <a href={doc.File} className="text-white flex items-center justify-center p-3" download={doc.File} target="_blank" rel="noreferrer">            
                        Descargar<IconsHi.HiDocumentDownload />
                        </a>  
                      </div>
                      <div className="btn-download">
                        <button className="text-white flex items-center justify-center p-3" onClick={()=>onDelete(doc.id, doc.Filename)}>            
                          <IconsRi.RiDeleteBin6Fill className="text-3xl"/>
                        </button>
                      </div>
                    </>
                  )
                }else if(type === "doc" || type === "validate"){
                  return(
                  <>
                    <div className="btn-download mr-2 ">
                      <a href={doc.OriginalFile.File} className="text-white flex items-center justify-center p-3" download={doc.OriginalFile.File} target="_blank" rel="noreferrer">            
                      Original<IconsHi.HiDocumentDownload />
                      </a>  
                    </div>
                    <div className="btn-download">
                      <a href={doc.SignedFile.File} className="text-white flex items-center justify-center p-3" download={doc.SignedFile.File} target="_blank" rel="noreferrer">            
                        Firmado<IconsHi.HiDocumentDownload />
                      </a>
                    </div>
                  </>
                  )                
                }else if(type === "user"){
                  return(
                  <>
                    <div className="btn-download mr-2">        
                      {doc.disable? 
                        <button onClick={() => onEnableUser(doc.id, doc.uid)} className="text-white flex items-center justify-center p-3"> 
                          <IconsFa.FaUserCheck className="text-3xl"/>
                        </button>:
                        <a href={`/editUser/${doc.id}`} className="text-white flex items-center justify-center p-3"> 
                          <IconsFa.FaUserEdit className="pl-1 text-3xl"/>
                        </a>
                      }      
                    </div>
                    <div className="btn-download">
                      <button className="text-white flex items-center justify-center p-3" onClick={() => onDisableUser(doc.id, doc.uid)}>            
                        {/* Delete Account */}
                        {doc.disable? <IconsRi.RiDeleteBin6Fill className="text-3xl"/>:<IconsFa.FaUserAltSlash className="pl-1 text-3xl"/>}      
                      </button>
                    </div>
                  </>
                  )                
                }
              })()}
            </div>
        
            {/* Information */}
            <div className="pt-10 pb-6 w-full px-4">
              {(() =>{
                if (type === "doc") {
                  return(
                    <>
                      <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.OriginalFile.Filename}</h1>
                      {/* <h2 className="font-medium leading-none text-base tracking-wider text-black-400 hidden"> {doc.SignedFile.CSV}</h2> */}
                      <b>CSV:</b> <h2 id={"csv_"+doc.id} className="font-medium leading-none text-base tracking-wider text-black-400 break-words" onClick={() => copy("csv_"+doc.id)}>{doc.SignedFile.CSV} <IconsRi.RiFileCopy2Line className="inline text-2xl"/></h2>
                    </>
                  )
                }else if (type === "validate") {
                  return(
                    <>
                      <h1 className="font-medium leading-none text-base tracking-wider text-black-400"><b>Documento:</b> {doc.OriginalFile.Filename}</h1>
                      <b>CSV:</b> <h2 id={"csv_"+doc.id} className="font-medium leading-none text-base tracking-wider text-black-400 break-words" onClick={() => copy("csv_"+doc.id)}>{doc.SignedFile.CSV} <IconsRi.RiFileCopy2Line className="inline text-2xl"/></h2>
                    </>
                  )
                }else if (type === "cert") {
                  return(
                    <h2 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.Filename}</h2>
                  )
                }else if(type === "user"){
                  return(
                    <div className="pt-10 pb-6 w-full px-4">
                      <div>
                        <label className="font-medium leading-none text-base tracking-wider text-black-400 font-semibold">Nombre:</label>
                        <h2 className="pl-5 break-words">{doc.name}</h2>
                      </div>
                      <div>
                        <label className="font-medium leading-none text-base tracking-wider text-black-400 font-semibold">Email:</label>
                        <p className="pl-5 break-words">{doc.email}</p>
                      </div>
                      <div>
                        <label className="font-medium leading-none text-base tracking-wider textblack-400 font-semibold">Rol:</label>
                        <p className="pl-5 break-words">{doc.role}</p>
                      </div>
                    </div>
                  )
                }
              })()}
              {/* <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{`${order}. ${title}`}</h1> */}
            </div>
          </div>
      </div>
    </>
  );
};

export default Card;