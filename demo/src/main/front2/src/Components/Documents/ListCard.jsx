import React, { useEffect, useState } from 'react';
import { firestore, storage } from '../../firebaseConfig'
import { toast } from 'react-toastify';
import Card from '../ExtraComponents/Card';
import Searchbar from '../ExtraComponents/Searchbar';
// import PdfTest from '../ExtraComponents/PdfTest'
// import Preview from '../ExtraComponents/Preview'
// import { useNavigate } from 'react-router-dom'
// import Login from '../ExtraComponents/Login';

const ListCard = () => {
  const [docs, setDocs] = useState([]);
  // const [url, setUrl] = useState([]);
  // const [docUrls, setDocUrl] = useState([]);

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
          await imageRef.delete();
          toast('Document Removed Successfully', {
              type: 'error',
              autoClose: 2000
          });
      }
  }

  useEffect(() => {
    const getDocs = () =>{
      const docs = [];
      firestore.collection('documents').onSnapshot(snapshot => {
        snapshot.forEach((doc, index) => {
          // storage.ref(`/originalDocuments/${doc.data().OriginalFile.Filename}`).getDownloadURL()
          //   .then((url) => {
          //     console.log("Download URL:",url);
          //     docUrls.push({...url, id: doc.id});
          // })
          // console.log("Index:",docUrls[index]);
          // console.log(doc.data());
          docs.push({...doc.data(), id: doc.id}); 
          // console.log(doc.data().SignedFile.CSV);
            // docUrls.push([doc.data().OriginalFile.File])
            // storage.ref().getDownloadURL()
        });
        // console.log(docs);
        setDocs(docs);
        // setUrl(urls);
        // setDocUrl(docUrls);

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
                <div key={index} className="col-md-4 docs-div">
                  <li key={index}>
                    {/*  filename={doc.OriginalFile.Filename */}
                    <Card onDeleteDoc={onDeleteDoc} index={index} doc={doc} url={doc.OriginalFile.File} type="doc" />
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