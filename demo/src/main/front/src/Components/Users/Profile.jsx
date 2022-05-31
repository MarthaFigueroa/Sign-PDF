import React from "react";
import * as IconsRi from "react-icons/ri";
import * as IconsFa from "react-icons/fa";
import TimeAgo from 'react-timeago';
import { UserContext } from "../../Providers/UserProvider";
import Navbar from "../Partials/Navbar";

const Profile = () => {

    const deleteProfile = () =>{
        
    }

    return (
        <>
            <Navbar />
            <div className="py-12 xl:h-full md:h-auto">
                {/* <div className="flex justify-center content-center flex-wrap"> */}
                {/* <div className="flex flex-wrap g-6 text-gray-800 content-center py-12 px-6 xl:h-full md:h-auto"> */}
                <div className="card xl:w-1/4 md:w-1/2 mobile:w-11/12 sm:w-9/12 mx-auto">
                    {/* {(() =>{ */}
                    <UserContext.Consumer>
                    {user =>{
                    if(user !== null){
                        return(
                            <>
                            {/* " style={{backgroundImage: (user.photoURL)}} */}
                                <div id={user.id} className="h-56 rounded-t-2xl overflow-hidden relative justify-center flex relative">
                                    {/* <img className="signersIcon mx-auto mb-12 bg-image w-full" src={user.photoURL} alt="" /> */}
                                    <img className="signersIcon h-56 w-56 mx-auto mb-12 absolute userImg" src={user.photoURL} alt="" />
                                </div>
                                <div className="flex relative mb-5">
                                    <div className="flex absolute right-0">
                                        <TimeAgo date={user.Created_at}/>
                                    </div>
                                </div>
                                <div className="relative bottom-0 left-0 -mb-4 mx-auto flex flex-row justify-center pt-2">
                                    <div className="btn-download mx-1">
                                        <a href={`/editUser/${user.id}`} className="text-white flex items-center justify-center p-3">            
                                        <IconsFa.FaUserEdit className="pl-1 text-3xl"/>
                                        </a>  
                                    </div>
                                    <div className="btn-download">
                                        <button className="text-white flex items-center justify-center p-3" onClick={deleteProfile}>            
                                            {/* Delete Account */}
                                            <IconsRi.RiDeleteBin6Fill className="text-3xl"/>
                                        </button>
                                    </div>
                                </div>

                                <div className="pt-10 pb-6 w-full px-4">
                                    <div>
                                        <label className="font-medium leading-none text-base tracking-wider text-black-400 font-semibold">Nombre:</label>
                                        <p className="pl-5">{user.name}</p>
                                    </div>
                                    <div>
                                        <label className="font-medium leading-none text-base tracking-wider text-black-400 font-semibold">Email:</label>
                                        <p className="pl-5">{user.email}</p>
                                    </div>
                                    <div>
                                        <label className="font-medium leading-none text-base tracking-wider textblack-400 font-semibold">Rol:</label>
                                        <p className="pl-5">{user.role}</p>
                                    </div>
                                </div>
                            </>
                            // <p>{user.name}</p>
                        )
                    }
                    }}
                    </UserContext.Consumer>
                    {/* })()} */}
                </div>
                {/* </div> */}
            </div>
        </>
    );
};

export default Profile;