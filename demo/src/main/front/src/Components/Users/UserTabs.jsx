import React from 'react'
import { useState, useEffect } from "react";
import { axiosBaseURL } from '../../Config/axios.js';
import Table from '../Prestamos/PrestamosTable.jsx'
import DisableUsersTable from '../Prestamos/returnedObjectsTable.jsx'
import DeletedUsersTable from '../Prestamos/deletedPrestamosTable.jsx'
import { Tabs } from 'react-bootstrap';

const UserTabs = () => { 
    const [users, setUsers] = useState([]);

    const onDeleteUser = async id =>{
        console.log(id);
        if(window.confirm("Are you sure you want to delete this user?")){
            // await firestore.collection('users').doc(id).delete();
            await axios.post(`/deleteUser`, id, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
                console.log(res.data);
              })
            toast('¡El usuario ha sido eliminado exitosamente!', {
                type: 'error',
                autoClose: 2000
            });
        }
    }

    async function onDisableUser(id, uuid){
        console.log(id);
        if(window.confirm("Está seguro que desea deshabilitar este usuario?")){
            // await firestore.collection('users').doc(id).delete();
            await axios.post(`/disableUser/${id}/${uuid}`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
                console.log(res.data);
              })
            toast('¡El usuario ha sido deshabilitado exitosamente!', {
                type: 'error',
                autoClose: 2000
            });
        }
    }
   
    useEffect(() => {
        const getUsers = async() =>{
            await axios.get(`/users`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
                console.log(res.data);
                setUsers(res.data);
              })
        }
        getUsers();
    }, []);

    return (
        <>
            <nav className="flex pl-0 mb-0 list-none flex-wrap border-b-2">
                <a href="" className="bg-none rounded-t text-center flex justify-center content-center list-none py-7 px-2.5">
                    prueba
                </a>
                <a href="" className="bg-none rounded-t text-center flex justify-center content-center list-none py-7 px-2.5">
                    prueba
                </a>
            </nav>
        </>
        // <Tabs id="controlled-tab-example">
        //     <Tabs eventKey="Usuarios" title="Usuarios">
        //         <>
        //             <Searchbar type="user"/>
        //             <main className="h-full md:h-screen w-full">
        //                 <section className="container mx-auto px-0 md:px-4 py-4">
        //                     <ul id="List">
        //                         <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
        //                             {users.map((user, index) =>{
        //                                 // {(()=>{
        //                                 if(!user.disable){
        //                                     return(
        //                                         <div key={index} className="docs-div mb-10">
        //                                             <li key={index}>
        //                                                 <Card onDisableUser={onDisableUser} index={index} doc={user} url={user.photoURL} type="user" />
        //                                             </li>
        //                                         </div>
        //                                     )
        //                                 }else{
        //                                     return (null)
        //                                 }
        //                                 // })()}
        //                             })}
        //                         </div>
        //                     </ul>                
        //                 </section>
        //             </main>
        //         </>
        //     </Tabs>
        //     <Tabs eventKey="UsuariosDeshabilitados" title="Usuarios Deshabilitados">
        //     <>
        //             <Searchbar type="user"/>
        //             <main className="h-full md:h-screen w-full">
        //                 <section className="container mx-auto px-0 md:px-4 py-4">
        //                     <ul id="List">
        //                         <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
        //                             {users.map((user, index) =>{
        //                                 // {(()=>{
        //                                 if(user.disable){
        //                                     return(
        //                                         <div key={index} className="docs-div mb-10">
        //                                             <li key={index}>
        //                                                 <Card onDelete={onDeleteUser} index={index} doc={user} url={user.photoURL} type="user" />
        //                                             </li>
        //                                         </div>
        //                                     )
        //                                 }else{
        //                                     return (null)
        //                                 }
        //                                 // })()}
        //                             })}
        //                         </div>
        //                     </ul>                
        //                 </section>
        //             </main>
        //         </>
        //     </Tabs>
        // </Tabs>
    );
}

export default UserTabs