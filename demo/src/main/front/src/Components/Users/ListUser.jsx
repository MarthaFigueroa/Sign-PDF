import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import axios from '../../axios.js';
import Card from '../Partials/Card';
import Searchbar from '../Partials/Searchbar';

const ListUser = ({users}) => {

    const [openTab, setOpenTab] = useState(1);
    const [newUsers, setUsers] = useState(users);

    const onDeleteUser = async id =>{
        if(window.confirm("Are you sure you want to delete this user?")){
            await axios.post(`/deleteUser`, id, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
              })
            toast('¡El usuario ha sido eliminado exitosamente!', {
                type: 'error',
                autoClose: 2000
            });
        }
    }

    async function onDisableUser(id, uuid){
        if(window.confirm("Está seguro que desea deshabilitar este usuario?")){
            await axios.post(`/disableUser/${id}/${uuid}`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
                setUsers(res.data);
                toast('¡El usuario ha sido deshabilitado!', {
                    type: 'error',
                    autoClose: 2000
                });
              })
        }
    }

    async function onEnableUser(id, uuid){
        if(window.confirm("Está seguro que desea habilitar este usuario?")){
            await axios.post(`/enableUser/${id}/${uuid}`, {
                headers: {
                    Accept: "application/json ,text/plain, */*"
                }
              })
              .then(async res => {
                setUsers(res.data);
                toast('¡El usuario ha sido habilitado exitosamente!', {
                    type: 'success',
                    autoClose: 2000
                });
              })
        }
    }
    
    useEffect(() => {
        setUsers(users);
    }, [users]);

    return (
        
        <>
            <div className="flex w-11/12 mx-auto justify-center">
                <div className="w-full">
                    <ul className="tab-list" role="tablist">
                        <li className="tab-list-item">
                            <a className={"text-xs font-bold uppercase px-5 py-3 shadow-lg block leading-normal rounded-t-lg " +
                                (openTab === 1
                                ? "text-active-item bg-white"
                                : "text-white bg-active-item")
                } onClick={e => { e.preventDefault(); setOpenTab(1);}} data-toggle="tab" href="#link1" role="tablist">
                            Usuarios Habilitados</a>
                        </li>
                        <li className="tab-list-item">
                            <a className={ "text-xs font-bold uppercase px-5 py-3 shadow-lg block leading-normal rounded-t-lg " +
                                (openTab === 2
                                    ? "text-active-item bg-white"
                                    : "text-white bg-active-item")
                            } onClick={e => {e.preventDefault(); setOpenTab(2);}} data-toggle="tab" href="#link2" role="tablist">Usuarios Deshabilitados</a>    
                        </li>
                    </ul>
                    <div className="tab-content">
                        <div className={openTab === 1 ? "block" : "hidden"} id="link1">
                            <Searchbar type="user"/>
                            <main className="h-full md:h-screen w-full">
                                <section className="container mx-auto px-0 md:px-4 py-4">
                                    <ul id="List">
                                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
                                            {newUsers.map((user, index) =>{
                                                if(!user.disable){
                                                    return(
                                                        <div key={index} className="docs-div mb-10">
                                                            <li key={index}>
                                                                <Card onDisableUser={onDisableUser} index={index} doc={user} url={user.photoURL} type="user" />
                                                            </li>
                                                        </div>
                                                    )
                                                }else{
                                                    return (null)
                                                }
                                            })}
                                        </div>
                                    </ul>                
                                </section>
                            </main>                            
                        </div>
                        <div className={openTab === 2 ? "block" : "hidden"} id="link2">
                            <Searchbar type="user"/>
                            <main className="h-full md:h-screen w-full">
                                <section className="container mx-auto px-0 md:px-4 py-4">
                                    <ul id="List">
                                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
                                            {newUsers.map((user, index) =>{
                                                if(user.disable){
                                                    return(
                                                        <div key={index} className="docs-div mb-10">
                                                            <li key={index}>
                                                                <Card onDelete={onDeleteUser} index={index} doc={user} url={user.photoURL} onDisableUser={onDisableUser} onEnableUser={onEnableUser} type="user" />
                                                            </li>
                                                        </div>
                                                    )
                                                }else{
                                                    return (null)
                                                }
                                            })}
                                        </div>
                                    </ul>                
                                </section>
                            </main>                            
                        </div>
                    </div>
                </div>
            </div>            
        </>
    );
}

export default ListUser;

