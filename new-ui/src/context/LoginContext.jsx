import {createContext, useContext, useState} from "react";
import {ApiContext} from "./ApiContext";
import {useDispatch} from "react-redux";
import PropTypes from "prop-types";
import {resetStore, setAuthId, setRole, setToken} from "../redux/actions.js"
import {API_GATEWAY_URL, AUTH_URL, LOGIN_URL} from "../constant/Endpoints.js";
import {useNavigate} from "react-router-dom";

export const LoginContext = createContext();

export const LoginContextProvider = ({children}) => {
    const {apiPost} = useContext(ApiContext);
    const dispatch = useDispatch();
    const [isLoading, setIsLoading] = useState(false);
    const [isLogin, setIsLogin] = useState(false);
    const navigate = useNavigate();

    async function handleLogin(payload) {
        setIsLoading(true);
        try {
            const responseData = await apiPost(`${API_GATEWAY_URL}${AUTH_URL}${LOGIN_URL}`, payload);
            if (responseData.status === 200) {
                dispatch(setRole(responseData.data.role));
                dispatch(setToken(responseData.data.token));
                dispatch(setAuthId(responseData.data.authId));
                setIsLogin(true);
            }
        }catch (error){
            console.log(error);
            setIsLogin(false);
        }
        setIsLoading(false);
    }

    function handleLogout() {
        dispatch(resetStore());
        navigate('/');
    }

    return (
        <LoginContext.Provider
            value={{
                handleLogin,
                handleLogout,
                isLoading,
                isLogin
            }}
        >
            {children}
        </LoginContext.Provider>
    );
};

LoginContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};