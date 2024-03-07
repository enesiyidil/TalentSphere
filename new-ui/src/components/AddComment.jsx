import * as React from 'react';
import {useContext, useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import styles from "../Css/AddComment.module.css";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {API_GATEWAY_URL, COMMENT_URL, SAVE_BY_PERSONAL} from "../constant/Endpoints.js";

export default function AddComment() {

    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const navigate = useNavigate();

    const [comment, setComment] = useState('')
    const [title, setTitle] = useState('')

    const handleClearClick = () => {
        setComment('')
        setTitle('')
    }

    const handleSaveClick = () => {
        apiPost(`${API_GATEWAY_URL}${COMMENT_URL}${SAVE_BY_PERSONAL}`, {
            content: comment,
            personalId: userProfile.id,
            companyId: userProfile.companyId,
            title: title,
        }, token);
        handleClearClick();
        navigate('/home');
    }


    return (
        <>
            <div style={{ width:'50%', height:'40%',paddingTop:'1.5rem',margin:'auto',  backgroundColor: 'rgba(26, 26, 26, 0.5)',
                display:'flex',textAlign:'center',flexDirection: 'column' }}>
                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': {m: 1, width: '25ch',backgroundColor:'white'},
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <div>
                        <TextField
                            required
                            id="comment"
                            label="Comment"
                            onChange={e => setComment(e.target.value)}
                            defaultValue={comment}
                        />
                    </div>

                    <div>
                        <TextField
                            required
                            id="title"
                            label="Title"
                            onChange={e => setTitle(e.target.value)}
                            defaultValue={title}
                        />
                    </div>
                </Box>
                <div className={styles["button"]}>
                    <button onClick={handleSaveClick} type="button" className={styles["button"]}>
                        Save
                    </button>
                    <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
                </div>
            </div>
        </>
    )
        ;
}