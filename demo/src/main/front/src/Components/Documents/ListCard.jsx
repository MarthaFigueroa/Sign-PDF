import React, { useEffect, useState } from 'react';
import { firestore, storage } from '../../firebaseConfig'
import { toast } from 'react-toastify';
import Card from '../ExtraComponents/Card';
import Searchbar from '../ExtraComponents/Searchbar';

const ListCard = () => {
  const [docs, setDocs] = useState([]);
  // const [url, setUrl] = useState([]);

  // const navigate = useNavigate();

  // const goTo = (route) =>{
  //     console.log("kkk");
  //     navigate(route);
  // }

  const onDeleteDoc = async (id, filename) =>{
      console.log(id);
      const confirmation = window.confirm("Are you sure you want to delete this document?");
      if(confirmation===true){
          await firestore.collection('documents').doc(id).delete();
          let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/originalDocuments/${filename}`);
          let signedImageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/signedDocuments/Signed_${filename}`);
          await imageRef.delete();
          await signedImageRef.delete();
          await toast('Document Removed Successfully', {
              type: 'error',
              autoClose: 2000
          });
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
    const getDocs = () =>{
      const docs = [];
      firestore.collection('documents').onSnapshot(snapshot => {
        snapshot.forEach((doc, index) => {
          docs.push({...doc.data(), id: doc.id}); 
        });
        setDocs(docs);
      });
    }
    getDocs();
  }, []);
  return (
    <div className='content'>
          {/* form-group input-group formField */}
          {/* mt-3 col-md-6 col-xs-2 offset-md-3 */}
      {/* <div key="searchBar" className="input-group w-1/2 mx-auto pt-3 sticky top-12">
        <label htmlFor="user" className="icon-group">
          <span className="m-auto px-2 h-12 text-white">
              <Icon.Person />
          </span>
        </label>
        <input id="doc_search" type="text" name="usuario" onKeyUp={searchUsr} aria-label="Search" placeholder="Buscar Documento por CSV" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1"/>
      </div> */}

      <Searchbar />
      <main className="h-full md:h-screen w-full">
        <section className="container mx-auto px-0 md:px-4 py-4">
          <ul id="List">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
              {docs.map((doc, index) =>(
                // {doc}
                <div key={index} className="docs-div">
                  <li key={index}>
                    {/*  filename={doc.OriginalFile.Filename */}
                    <Card onDeleteDoc={onDeleteDoc} index={index} doc={doc} url={doc.OriginalFile.File} type="doc" pdfPreview={pdfPreview} />
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