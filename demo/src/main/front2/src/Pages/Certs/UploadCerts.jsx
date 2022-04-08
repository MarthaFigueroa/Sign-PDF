import React from 'react';
import NavBar from '../../Components/Navbar'
import UploadCert from '../../Components/Certificates/UploadCert.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UploadCerts = () => {
    
    return(
        <div>
            <NavBar />
            <div className="w-full p-4">
                <div className="max-w-100">
                    <ToastContainer />
                    <div className="flex w-full justify-center">
                        <div className="relative flex flex-col bg-gray-100 rounded-xl shadow-lg border  xl:w-1/4 md:w-1/2">
                            {/* <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Upload New Cert</div>
                            <div className="flex p-2"> */}
                                <UploadCert />
                            {/* </div> */}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
}

export default UploadCerts;