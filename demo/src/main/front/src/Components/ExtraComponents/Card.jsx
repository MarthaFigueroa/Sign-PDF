import React, { useEffect } from "react";
// import PdfTest from '../ExtraComponents/PdfTest'
import { storage } from '../../firebaseConfig'

import { pdfjs } from 'react-pdf';
// import classNames from "classnames";
import { HiDocumentDownload } from "react-icons/hi";
// import { BsChatSquareFill } from "react-icons/bs";

// import styles from "./Card.module.css";

const Card = ({ image, doc, url, docUrls }) => {
  // const [docPreview, setDocPreview] = useState("");

  // const handlePreview = ()=>{
  //   setDocPreview(URL.createObjectURL(doc.OriginalFile.File));
  // }

  // var pdfjsLib = window['pdfjs-dist/build/pdf'];

  // pdfjsLib.GlobalWorkerOptions.workerSrc = 'pdf.worker.min.js';
  const pdfPreview = (url) =>{

    console.log(url);
    // window.location.href = url;
    // let newUrl = window.location.href;
    // window.location.href = "/cards"
    // console.log(newUrl);
    // let blob = new Blob([url]);
    // console.log(blob);
    // let newUrl = URL.createObjectURL(blob);
    // console.log(newUrl);
    // url = storage.ref(url).getDownloadURL()
    console.log(url);
    
    // Loaded via <script> tag, create shortcut to access PDF.js exports.
    var pdfjsLib = window['pdfjs-dist/build/pdf'];

    // The workerSrc property shall be specified.
    pdfjsLib.GlobalWorkerOptions.workerSrc = '//mozilla.github.io/pdf.js/build/pdf.worker.js';

    // Asynchronous download of PDF
    var loadingTask = pdfjsLib.getDocument(url);
    loadingTask.promise.then(function(pdf) {
      console.log('PDF loaded');
      
      // Fetch the first page
      var pageNumber = 1;
      pdf.getPage(pageNumber).then(function(page) {
        console.log('Page loaded');
        
        var scale = 0.5;
        var viewport = page.getViewport({scale: scale});

        // Prepare canvas using PDF page dimensions
        // var canvas = document.getElementById('the-canvas');
        var div = document.getElementById(doc.id);
        var canvas = document.createElement('canvas');
        // canvas.classList.add("object-cover w-full h-full");
        var context = canvas.getContext('2d');
        canvas.height = 224;
        canvas.width = 297;
        div.appendChild(canvas);
        // Render PDF page into canvas context
        var renderContext = {
          canvasContext: context,
          viewport: viewport
        };
        var renderTask = page.render(renderContext);
        renderTask.promise.then(function () {
          console.log('Page rendered');
        });
      });
    }, function (reason) {
      // PDF loading error
      console.error(reason);
    });

  }

  useEffect(() => {
    // let url = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2FCloud HP Helion.pdf?alt=media"
    pdfPreview(url);
  }, [doc])
  

  return (
    <div className="hover:bg-green-300 shadow-xl hover:shadow-none cursor-pointer w-80 rounded-3xl flex flex-col items-center justify-center transition-all duration-500 bg-gray-100 ease-in-out">
      <div className="relative mt-2 mx-2">
        <div id={doc.id} className="h-56 rounded-2xl overflow-hidden">
          {/* <img src={url} className="object-cover w-full h-full" alt="" /> */}
          {/* <canvas id="the-canvas"></canvas> */}
        {/* <PdfTest url={url}/>   */}
        </div>
        {/* {image!==""? "bottom-0 left-0 -mb-4 ml-3 flex flex-row": "absolute bottom-0 left-0 -mb-4 ml-3 flex flex-row"} */}
        <div className=" bottom-0 left-0 -mb-4 ml-3 flex flex-row">
          {/* absolute */}
          <div className="h-10 bg-blue-400 flex items-center justify-center text-xl hover:bg-blue-500 text-white hover:text-red-500 rounded-2xl shadow-xl transform-gpu translate-y-0 hover:-translate-y-1 transition-all duration-300 ease-in-out">
            <a href="https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2FCP%205%20El%20registro%20contable%20de%20las%20transacciones%20II%20(I).pdf?alt=media&token=a826583e-c833-456d-85b7-890b0add8417" className="text-white flex items-center justify-center p-3 no-underline" download={doc.OriginalFile.File} target="_blank" rel="noreferrer">            

            {/* <a href="https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2FCP%205%20El%20registro%20contable%20de%20las%20transacciones%20II%20(I).pdf?alt=media&token=a826583e-c833-456d-85b7-890b0add8417"></a> */}
            
            Original<HiDocumentDownload />
            </a>  
          </div>
          <div className="h-10 bg-blue-400 flex items-center justify-center text-xl hover:bg-blue-500 text-white hover:text-red-500 rounded-2xl shadow-xl transform-gpu translate-y-0 hover:-translate-y-1 transition-all duration-300 ease-in-out">
            <a href={doc.SignedFile.Signed_file} className="text-white flex items-center justify-center p-3 no-underline" download={doc.SignedFile.Signed_file} target="_blank" rel="noreferrer">            
              Firmado<HiDocumentDownload />
            </a>
          </div>

          {/* <div
            className="h-10 w-16 ml-2 bg-blue-400 hover:bg-blue-500 flex items-center justify-center font-medium text-white hover:text-red-500 rounded-2xl shadow-xl transform-gpu translate-y-0 hover:-translate-y-1 transition-all duration-300 ease-in-out group "
          >
              <BsChatSquareFill />
            <span className="text-white ml-2 hover:text-white">
              {likes}
            </span>
          </div> */}
        </div>
      </div>
      <div className="pt-10 pb-6 w-full px-4">
        <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{doc.OriginalFile.Filename}</h1>
        {/* <h1 className="font-medium leading-none text-base tracking-wider text-black-400">{`${order}. ${title}`}</h1> */}
      </div>
    </div>
  );
};

export default Card;