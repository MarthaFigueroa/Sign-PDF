import React from 'react';
// import * as Icon from "@material-ui/icons";
import * as Icon from "@mui/icons-material";


const Searchbar = ({type}) => {
    
    const searchUsr = () => {
        var input, filter, ul, li, h2, i, txtValue,div;
        input = document.getElementById("Search"); 
        filter = input.value.toUpperCase(); 
        ul = document.getElementById("List"); 
        li = ul.getElementsByTagName("li");        
        // console.log(li.length);
        div = document.getElementsByClassName("docs-div");
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
    
    return (
        <>
            <div key="searchBar" className="input-group w-1/2 mx-auto pt-3">
                <label htmlFor="user" className="icon-group">
                <span className="m-auto px-2 h-12 text-white">
            {(()=>{
                if(type === 'validate' || type === 'doc'){
                    return(
                        <Icon.DocumentScanner />
                    )
                }else if(type === "cert" || type === "user"){
                    return(
                        <Icon.Person />
                    )
                }
            })()}
            </span>
                </label>
                <input id="Search" type="text" name="CSV" onKeyUp={searchUsr} aria-label="Search" placeholder={type === 'user' || type === 'cert'? "Buscar Usuario por Nombre": "Buscar Documento por CSV"} className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1"/>
            </div>
        </>
    )
}

export default Searchbar