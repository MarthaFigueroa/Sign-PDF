import React, { useEffect } from "react";
import * as IconsHi from "react-icons/hi";
import * as IconsAi from "react-icons/ai";
import * as IconsRi from "react-icons/ri";
import * as IconsFa from "react-icons/fa";
import TimeAgo from 'react-timeago';
import image from "./../../public/img/signerUser.png";
import { UserContext } from "../../Providers/UserProvider";

const Card = ({ doc, url, onDelete, index, type, onDisableUser }) => {

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
            scale = 0.5;
          }
          
          var viewport = page.getViewport({scale: scale});
          // Prepare canvas using PDF page dimensions
          // var canvas = document.getElementById('the-canvas');
          // var canvas = document.getElementById(index);
          var div = document.getElementById(doc.id);
          var existingCanvas = document.getElementById(index);
          if(div.contains(existingCanvas)){
            removePreview(index);
          }
            var canvas = document.createElement('canvas');
            canvas.id = index;
            var context = canvas.getContext('2d');
            // canvas.height = 224;
            // canvas.width = 420;
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
      });
    }

    const removePreview = (index) =>{
      let canvas = document.getElementById(index);
      canvas.remove();
    }

    if(type === "doc" || type === "validate"){
      pdfPreview(url, doc, index);
      console.log("Render :)");
      // formatterDate(doc.OriginalFile.Created_at);
    }
  }, [url, doc, index, type])
  
  return (
    <>
      <div className={type==="validate"? "card w-full": type==="user"? "card mt-36": "card"}>
          {(()=>{
            if(type === "cert"){
              return(
                <div id={doc.id} className="h-56 rounded-t-2xl overflow-hidden relative w-full justify-center flex">
                  <img className="signersIcon" src={image} alt="Signers User" />
                  
                </div>
              )
            }else if(type === "doc"){
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
                      <img className="signersIcon h-56 w-56 mx-auto mb-12 absolute userImg" referrerPolicy="no-referrer" src={doc.photoURL} alt="" />
                    </div>
                  </div>
                </>
              )
            }
          })()}

          <div className="absolute right-0">
            <UserContext.Consumer>
              {user =>{
                if(user !== null){
                  if(user.role === "admin"){
                    if(type === "cert"){
                      return(
                      <button className="drop-shadow-lg" onClick={()=>onDelete(doc.id, doc.Filename)}>
                        <IconsAi.AiFillCloseCircle className="text-2xl text-slate-400 " />
                      </button>
                      )                
                    }else if(type === "doc"){
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

          <div className={type==="user"? "mt-16": ""}>
            <div className="flex relative mb-5">
              <div className="flex absolute right-0">
                {(()=>{
                if(type === "cert" || type === "user"){
                  return(
                    <TimeAgo date={doc.Created_at}/>
                  )
                }else if(type === "doc" || type === "validate"){
                  return(
                    <TimeAgo date={doc.OriginalFile.Created_at}/>
                  )
                }
              })()}
              </div>
            </div>
            <div className="relative bottom-0 left-0 -mb-4 mx-auto flex flex-row justify-center pt-2">
              {(() =>{
                if(type === "cert"){
                  return(
                    <div className="btn-download">
                      <a href={doc.File} className="text-white flex items-center justify-center p-3" download={doc.File} target="_blank" rel="noreferrer">            
                      Certificado<IconsHi.HiDocumentDownload />
                      </a>  
                    </div>
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
                      <a href={`/editUser/${doc.id}`} className="text-white flex items-center justify-center p-3">            
                      <IconsFa.FaUserEdit className="pl-1 text-3xl"/>
                      </a>  
                    </div>
                    <div className="btn-download">
                      <button className="text-white flex items-center justify-center p-3" onClick={() => onDisableUser(doc.id, doc.uid)}>            
                        {/* Delete Account */}
                        <IconsRi.RiDeleteBin6Fill className="text-3xl"/>
                      </button>
                    </div>
                  </>
                  )                
                }
              })()}
            </div>

            <div className="pt-10 pb-6 w-full px-4">
              {(() =>{
                if (type === "doc" || type === "validate") {
                  return(
                    <>
                      <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.OriginalFile.Filename}</h1>
                      <h2 className="font-medium leading-none text-base tracking-wider text-black-400 hidden">CSV: {doc.SignedFile.CSV}</h2>
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