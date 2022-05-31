import React, { useEffect, useState, useContext, useCallback } from 'react';
import { storage } from '../../Config/config'
import { toast } from 'react-toastify';
import Card from '../Partials/Card';
import { useNavigate } from "react-router-dom";
import Searchbar from '../Partials/Searchbar';
import { UserContext } from "../../Providers/UserProvider";
import axios from '../../axios.js';

const ListCard = () => {
  const [docs, setDocs] = useState([]);
  const user = useContext(UserContext);
  // const [url, setUrl] = useState([]);

  const navigate = useNavigate();

  const goTo = useCallback((route) =>{
      console.log("kkk");
      navigate(route);
  }, [navigate])

  const onDelete = async (id, filename) =>{
      console.log(id);
      const confirmation = window.confirm("Are you sure you want to delete this document?");
      if(confirmation===true){
        await axios.post(`/deleteDocument/${id}`, {
          headers: {
              Accept: "application/json ,text/plain, */*"
          }
        })
        .then(async res => {
          console.log(res.data);
          let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/originalDocuments/${filename}`);
          let signedImageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/signedDocuments/Signed_${filename}`);
          await imageRef.delete();
          await signedImageRef.delete();
          toast('¡El documento ha sido eliminado exitosamente!', {
              type: 'error',
              autoClose: 2000
          });
          setDocs(res.data);
        })  
      }
  }

  const pdfPreview = (url, doc, index) =>{
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
        // var div = document.getElementById(doc.id);
        var canvas = document.getElementById(index);
        // console.log(lastCanvas);
        // if(lastCanvas){
        //   var canvas = document.createElement('canvas');
        //   canvas.id = index;
        // }
        var context = canvas.getContext('2d');
        // canvas.height = 224;
        // canvas.width = 320;
        // div.appendChild(canvas);
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

  useEffect(() => {
    const getDocs = async () =>{
      await axios.get(`/documents`, {
        headers: {
            Accept: "application/json ,text/plain, */*"
        }
      })
      .then(async res => {
        console.log(res.data);
        setDocs(res.data);
      })
    }
    getDocs();
  }, [user, goTo]);
  return (
    <div className='content'>
      <Searchbar type='doc'/>
      <main className="h-full md:h-screen w-full">
        <section className="container mx-auto px-0 md:px-4 py-4">
          <ul id="List">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
              {docs.map((doc, index) =>(
                // {doc}
                <div key={index} className="docs-div mb-10">
                  <li key={index}>
                    {/*  filename={doc.OriginalFile.Filename */}
                    <Card onDelete={onDelete} index={index} doc={doc} url={doc.OriginalFile.File} type="doc" pdfPreview={pdfPreview} />
                    {/* {doc.url} docUrls={doc.url}*/}
                  </li>
                </div>
              ))}
            </div>
              </ul> 
          </section>
        </main>
      {/* <Login /> */}
        {/* <PdfTest url={url}/>   */}

      {/* <Preview /> */}
    </div>
  )
}

export default ListCard