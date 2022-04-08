import React, { useEffect, useState } from 'react';
import CardDiv from '../ExtraComponents/CardDiv'
// import PdfTest from '../ExtraComponents/PdfTest'
// import Preview from '../ExtraComponents/Preview'
import { firestore, storage } from '../../firebaseConfig'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify';
import Login from '../ExtraComponents/Login';
import Card from '../ExtraComponents/Card';
// import { Link } from 'react-router-dom';
// import styles from "./Card.module.css";

const ListCard = ({ title, likes, order, image }) => {
  const [docs, setDocs] = useState([]);
  const [url, setUrl] = useState([]);
  const [docUrls, setDocUrl] = useState([]);

  const navigate = useNavigate();

  const goTo = (route) =>{
      console.log("kkk");
      navigate(route);
  }

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

  const searchUsr = () => {
      var input, filter, ul, li, h2, i, txtValue,div;
      input = document.getElementById("doc_search"); 
      filter = input.value.toUpperCase(); 
      ul = document.getElementById("usersList"); 
      li = ul.getElementsByTagName("li");        console.log(li.length);
      div = document.getElementsByClassName(" docs-div");
      for (i = 0; i < li.length; i++) {  
      h2 = li[i].getElementsByTagName("h2")[0]; 
      txtValue = h2.textContent || h2.innerText; //a.textContent || a.innerText; 
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
          li[i].style.display = "";
          div[i].style.visibility="visible";
          div[i].style.position="relative";
      } else {
          li[i].style.display = "none";
          div[i].style.visibility="hidden";
          div[i].style.position="absolute";
      }
      }
  }
  useEffect(() => {
    const getDocs = () =>{
      const docs = [];
      const urls = [];
      const docUrls = [];
      firestore.collection('documents').onSnapshot(async snapshot => {
        snapshot.forEach(async (doc, index) => {
          // storage.ref(`/originalDocuments/${doc.data().OriginalFile.Filename}`).getDownloadURL()
          //   .then((url) => {
          //     console.log("Download URL:",url);
          //     docUrls.push({...url, id: doc.id});
          // })
          // console.log("Index:",docUrls[index]);
          console.log(doc.data());
          docs.push({...doc.data(), id: doc.id}); 
          urls.push([doc.data().OriginalFile.File]);
          
            // docUrls.push([doc.data().OriginalFile.File])
            // storage.ref().getDownloadURL()
        });
        console.log(docs);
        await setDocs(docs);
        // setUrl(urls);
        // setDocUrl(docUrls);

      });
    }
    getDocs();
  }, []);
  return (
    <>
      {/* <div key="searchBar" className="mt-3 col-md-6 col-xs-2 offset-md-3">
        <div className="form-group input-group formField">
          <div className="input-group-text icon searchIconDiv">
            <i className="material-icons searchIcon">search</i>
          </div>
          <input className="form-control" type="text" placeholder="Buscar usuario" id="doc_search" onKeyUp={searchUsr} aria-label="Search"/>
        </div>
      </div> */}
      <main className="h-full md:h-screen w-full">
        <section className="container mx-auto px-0 md:px-4 py-4">
              <ul id="usersList">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
              {docs.map((doc, index) =>(
                // {doc}
                <>
                  <Card key={index} doc={doc} url={doc.OriginalFile.File} />
                  {/* {doc.url} docUrls={doc.url}*/}
                </>
              ))}
            </div>
              </ul> 
          </section>
        </main>
      {/* <Login /> */}
        {/* <PdfTest url={url}/>   */}

      {/* <Preview /> */}
    </>
  )
}

export default ListCard