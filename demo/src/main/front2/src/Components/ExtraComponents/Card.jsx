// import { Icon } from "@material-ui/core";
import React, { useEffect } from "react";
// import PdfTest from '../ExtraComponents/PdfTest'
// import classNames from "classnames";
import * as Icons from "react-icons/hi";
import * as Icon from "@material-ui/icons";
// import { BsChatSquareFill } from "react-icons/bs";

// import styles from "./Card.module.css";

const Card = ({ doc, url, onDeleteDoc, index, type }) => {
  
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
          var scale = 0.5;
          var viewport = page.getViewport({scale: scale});
  
          // Prepare canvas using PDF page dimensions
          // var canvas = document.getElementById('the-canvas');
          var div = document.getElementById(doc.id);
          var canvas = document.createElement('canvas');
          canvas.id = index;
          // canvas.classList.add("object-cover w-full h-full");
          var context = canvas.getContext('2d');
          canvas.height = 224;
          canvas.width = 320;
          div.appendChild(canvas);
          // Render PDF page into canvas context
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
    if(type === "doc"){
      pdfPreview(url);
      // let params = [url, doc.id, index];
    }
    else {
      console.log("gg");

    }
  }, [url, doc.id, index, type])
  

  return (
    // hover:shadow-none
    <div className="card">
      <div id={doc.id} className="h-56 rounded-t-2xl overflow-hidden relative w-full">
        <div className="absolute right-0 mr-3">
          {(() =>{
            if(type === "cert"){
              return(
              <button onClick={()=>onDeleteDoc(doc.id, doc.Filename)}>
                <Icon.Close />
              </button>
              )                
            }else if(type === "doc"){
              return(
                <button onClick={()=>onDeleteDoc(doc.id, doc.OriginalFile.Filename)}>
                  <Icon.Close />
                </button>
              )
            }
          })()}
        </div>
      </div>
      <div className=" bottom-0 left-0 -mb-4 mx-auto flex flex-row justify-center pt-2">
        {/* absolute */}
        {(() =>{
          if(type === "cert"){
            return(
              <div className="btn-download">
                <a href={doc.File} className="text-white flex items-center justify-center p-3" download={doc.File} target="_blank" rel="noreferrer">            
                Certificado<Icons.HiDocumentDownload />
                </a>  
              </div>
            )
          }else if(type === "doc"){
            return(
            <>
              <div className="btn-download">
                <a href={doc.OriginalFile.File} className="text-white flex items-center justify-center p-3" download={doc.OriginalFile.File} target="_blank" rel="noreferrer">            
                Original<Icons.HiDocumentDownload />
                </a>  
              </div>
              <div className="btn-download">
                <a href={doc.SignedFile.File} className="text-white flex items-center justify-center p-3" download={doc.SignedFile.File} target="_blank" rel="noreferrer">            
                  Firmado<Icons.HiDocumentDownload />
                </a>
              </div>
            </>
            )                
          }
        })()}
      </div>

      <div className="pt-10 pb-6 w-full px-4">
        {(() =>{
          if (type === "doc") {
            return(
              <>
                <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.OriginalFile.Filename}</h1>
                <h2 className="font-medium leading-none text-base tracking-wider text-black-400">CSV: {doc.SignedFile.CSV}</h2>
              </>
            )
          }else if (type === "cert") {
            return(
              <h2 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.Filename}</h2>
            )
          }
        })()}
        {/* <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{`${order}. ${title}`}</h1> */}
      </div>
    </div>
  );
};

export default Card;