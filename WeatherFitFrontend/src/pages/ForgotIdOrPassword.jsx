import { useSearchParams } from "react-router-dom";
import {ContentsWrapper} from "../layout/mainPage.style.js";

const FindId = () => {
    return(
        <>
            아이디찾는 페이지
        </>
    );
}
const FindPw = () => {
    return(
        <>
            비밀번호 찾는 페이지
        </>
    );
}

const ForgotIdOrPassword = () => {
    const [searchParams] = useSearchParams();
    const page = searchParams.get("page");

    return (
        <ContentsWrapper>
            {page === "id" && <FindId />}
            {page === "pw" && <FindPw />}
        </ContentsWrapper>
    );
};

export default ForgotIdOrPassword;