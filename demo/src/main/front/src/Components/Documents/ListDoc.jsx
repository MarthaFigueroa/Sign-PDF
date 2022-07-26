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

  const navigate = useNavigate();

  const goTo = useCallback((route) =>{
      navigate(route);
  }, [navigate])

  const onDelete = async (id, filename) =>{
      const confirmation = window.confirm("Are you sure you want to delete this document?");
      if(confirmation===true){
        await axios.post(`/deleteDocument/${id}`, {
          headers: {
              Accept: "application/json ,text/plain, */*"
          }
        })
        .then(async res => {
          let imageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/originalDocuments/${filename}`);
          let signedImageRef = storage.refFromURL(`gs://validacion-de-documentos.appspot.com/signedDocuments/Signed_${filename}`);
          await imageRef.delete();
          await signedImageRef.delete();
          toast('¡El documento ha sido eliminado!', {
              type: 'error',
              autoClose: 2000
          });
          setDocs(res.data);
        })  
      }
  }

  useEffect(() => {
    const getDocs = async () =>{
      await axios.get(`/documents`, {
        headers: {
            Accept: "application/json ,text/plain, */*"
        }
      })
      .then(async res => {
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
                    <Card onDelete={onDelete} index={index} doc={doc} url={doc.OriginalFile.File} type="doc" />
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