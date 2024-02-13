import { createContext, useContext, useState, useEffect } from "react";
import { ApiContext } from "./ApiContext";
import PropTypes from "prop-types";
import {ManagerContext} from "./ManagerContext.jsx";
import {useDispatch, useSelector} from "react-redux";
import {addHoliday, deleteHoliday, setData} from "../redux/actions.js";

export const PersonalContext = createContext();

export const PersonalContextProvider = ({ children }) => {
    const userProfile = useSelector((state) => state.userProfile);
    const data = useSelector((state) => state.data);
    const token = useSelector((state) => state.token);
    const authId = useSelector((state) => state.authId);
    const dispatch = useDispatch();
    const { apiGet, apiPost, apiPatch, apiDelete } = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);
    const [personal, setPersonal] = useState({
        company: {},
        holidays: []
    });

    async function getData() {
        setIsLoading(true);
        const responseDataCompany = await apiGet(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_COMPANY_URL}${process.env.REACT_APP_FIND_BY_ID_URL}?id=${userProfile.companyId}`, token);
        setPersonal(prevState => ({...prevState, company: responseDataCompany.data}));

        const responseDataHolidays = await apiGet(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_FIND_ALL_BY_PERSONAL_ID_URL}?personalId=${userProfile.id}`, token);
        setPersonal(prevState => ({...prevState, holidays: responseDataHolidays.data}));

        // todo: data başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(setData(personal))
        setIsLoading(false);
    }

    async function handleAddHoliday(holiday){
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_SAVE_URL}`, holiday, token);
        dispatch(addHoliday(responseData.data));
        setIsLoading(false);
    }

    async function handleDeleteHoliday(id){
        setIsLoading(true);
        const responseData = await apiDelete(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_DELETE_BY_ID_URL}?id=${id}\``, token);
        dispatch(deleteHoliday(id));
        setIsLoading(false);
    }

    return (
        <PersonalContext.Provider
            value={{
                getData,
                handleAddHoliday,
                handleDeleteHoliday,
                isLoading,
            }}
        >
            {children}
        </PersonalContext.Provider>
    );
};

PersonalContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};