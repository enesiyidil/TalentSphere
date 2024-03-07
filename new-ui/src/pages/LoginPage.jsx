import styles from "../Css/LoginPage.module.css";
import {useForm} from "react-hook-form";
import {useContext, useEffect, useState} from "react";
import {LoginContext} from "../context/LoginContext.jsx";
import {useNavigate} from "react-router-dom";
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';

const LoginPage = () => {

    const {handleLogin, isLoading, isLogin} = useContext(LoginContext);
    const navigate = useNavigate();
    const [isSubmit, setIsSubmit] = useState(false);

    const {
        register,
        reset,
        formState: {errors, isSubmitting},
        getValues
    } = useForm();

    const handleClearClick = () => {
        reset();
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        handleLogin(getValues());
        setIsSubmit(true);
    }

    useEffect(() => {
        if (isLogin)
            navigate('/home');
    }, [isLogin]);

    return (
        <>


            {isLoading ? (<Box sx={{display: 'flex'}}>
                <CircularProgress/>
            </Box>) : (<div className={styles["register-form-wrapper"]}>
                <form onSubmit={handleSubmit} className={styles["register-form"]}
                      style={(!isLogin && isSubmit) ? {backgroundColor: "red"} : {}}>
                    {(!isLogin && isSubmit) &&
                        <p style={{color: "yellow", fontStyle: "oblique",}}>Email or password incorrect</p>}
                    <label className={styles["label"]}>
                        <span className={styles["label-inside-text"]}>E-Mail: </span>
                        <input
                            {...register("email", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'email'}
                            placeholder={'E-Mail'}
                            className={`${styles["textarea"]} ${styles["input"]}`}
                        />
                        {errors['email'] && (
                            <p className="text-red-500">{errors['email'].message}</p>
                        )}
                    </label>


                    <label className={styles["label"]}>
                        <span>Password: </span>
                        <input
                            {...register("password", {
                                required: `Cannot be left blank!`,
                            })}
                            type={'password'}
                            placeholder={'Password'}
                            className={styles["textarea"]}
                            minLength="4"
                        />
                        {errors['password'] && (
                            <p className="text-red-500">{errors['password'].message}</p>
                        )}
                    </label>

                    <div className={styles["button"]}>
                        <button disabled={isSubmitting} type="submit" className={styles["button"]}>
                            Submit
                        </button>
                        <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
                    </div>
                </form>
            </div>)}

        </>
    )
}

export default LoginPage;