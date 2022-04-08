const File = (props) => {
    // console.log(props.userImg)
    // if (props.currentStep !== 1) {
    //     return null
    // }
    return(
        <>        
            <div className="upload">
                    <label htmlFor="profile" />
            </div>
    
            {/* <input type="file" name='file' id='file' onChange={handleFiles} accept=".pdf"/> */}
            <input id="file" name="file" type="file" accept=".pdf" onChange={props.handleFileUpload}/>
        </>        
    ) 
}

export default File;