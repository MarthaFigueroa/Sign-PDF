import React from 'react';
import NavBar from '../../Components/ExtraComponents/Navbar'
import ValidateFile from '../../Components/Documents/ValidateFile.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ValidateFiles = ({type}) => {
    
    return(
        <div>
            <NavBar />
            <div className="w-full p-4">
                <div className="max-w-100">
                    <ToastContainer />
                    {/* <div className="flex w-full justify-center"> */}
                        {/* <div className="relative flex flex-col bg-gray-100 rounded-xl shadow-lg border  xl:w-1/4 md:w-1/2"> */}
                            <ValidateFile/>
                        {/* </div> */}
                    {/* </div> */}
                </div>
            </div>
        </div>
        )
}

export default ValidateFiles;