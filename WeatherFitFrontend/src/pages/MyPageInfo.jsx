import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import { Link, useNavigate } from "react-router-dom";   // ✅ useNavigate 추가
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";
import { BtnSection, CheckboxWrapper, DuplicateTestBtn} from "../layout/JoinTheMembership.style.js";
import { MyPageText, RegisterWrapper  } from "../layout/Mypage_info.style.js";

const MyPageInfo = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [selectedRadio, setSelectedRadio] = useState("men");
    const [isUserNameAvailable, setIsUserNameAvailable] = useState(false);
    const [isEmailAvailable, setIsEmailAvailable] = useState(false);

    const userNameRef = useRef(null);
    const emailRef = useRef(null);
    const navigate = useNavigate(); // ✅ 페이지 이동용

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    const checkUserNameAvailability = async () => {
        if (userName.trim() === "") {
            alert("사용자명을 입력해주세요.");
            userNameRef.current?.focus();
            return;
        }
        try {
            await axios.get(`/api/auth/check-username?username=${userName}`); // ✅ 진짜 서버 요청
            setIsUserNameAvailable(true);
            alert("사용 가능한 아이디입니다.");
        } catch (error) {
            setIsUserNameAvailable(false);
            alert(error.response?.data?.message || "이미 사용중인 아이디입니다.");
        }
    };

    const checkUserEmailAvailability = async () => {
        if (email.trim() === "") {
            alert("이메일을 입력해주세요.");
            emailRef.current?.focus();
            return;
        }
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("유효한 이메일 주소를 입력해주세요.");
            emailRef.current?.focus();
            return;
        }
        try {
            await axios.get(`/api/auth/check-email?email=${email}`); // ✅ 진짜 서버 요청
            setIsEmailAvailable(true);
            alert("사용 가능한 이메일입니다.");
        } catch (error) {
            setIsEmailAvailable(false);
            alert(error.response?.data?.message || "이미 사용중인 이메일입니다.");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (userName.trim() === "") {
            alert("사용자명을 입력해주세요.");
            userNameRef.current?.focus();
            return;
        } else if (email.trim() === "") {
            alert("이메일을 입력해주세요.");
            emailRef.current?.focus();
            return;
        }

        if (!isUserNameAvailable) {
            alert("아이디 중복 확인을 해주세요.");
            return;
        }
        if (!isEmailAvailable) {
            alert("이메일 중복 확인을 해주세요.");
            return;
        }

        try {
            const gender = selectedRadio === "men" ? "MALE" : "FEMALE";

            await axios.post("/api/auth/signup", {
                username: userName,
                email,
                gender,
            });

            alert("정보 수정이 완료되었습니다.");

            // ✅ 자동 로그인 바로 시도
            const response = await axios.post("/api/auth/login", {
                username: userName,
                password: password,
            });

            const { token, username: loggedInUsername } = response.data;
            localStorage.setItem("token", token);
            localStorage.setItem("username", loggedInUsername);

            // ✅ 메인페이지로 이동
            navigate("/");
        } catch (error) {
            console.error(error.response?.data);
            alert(error.response?.data?.message || "내 정보 수정 실패");
        }
    };

    return (
        <RegisterWrapper onSubmit={handleSubmit}>
            <MyPageText>내 정보 수정</MyPageText>

            <LoginSection>
                {/* 사용자명 입력 + 중복검사 */}
                <BtnSection style={{ marginTop: '5px' }}>
                    <LoginInput
                        ref={userNameRef}
                        type="text"
                        style={{ width: '390px' }}
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        $borderColor={theme[season].borderColor}
                        $bgColor={theme[season].bgColor}
                        $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
                        placeholder="사용자명을 입력해주세요"
                    />
                    <DuplicateTestBtn
                        type="button"
                        onClick={checkUserNameAvailability}
                        $borderColor={theme[season].borderColor}
                        $bgColor={theme[season].bgColor}
                        $focusColor={theme[season].focusColor}
                        $textColor={theme[season].textColor}
                        $hoverBgColor={theme[season].focusColor}
                    >
                        중복검사
                    </DuplicateTestBtn>
                </BtnSection>

                {/* 이메일 입력 + 중복검사 */}
                <BtnSection>
                    <LoginInput
                        ref={emailRef}
                        type="email"
                        style={{ width: '390px' }}
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        $borderColor={theme[season].borderColor}
                        $bgColor={theme[season].bgColor}
                        $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
                        placeholder="이메일을 입력해주세요"
                    />
                    <DuplicateTestBtn
                        type="button"
                        onClick={checkUserEmailAvailability}
                        $borderColor={theme[season].borderColor}
                        $bgColor={theme[season].bgColor}
                        $focusColor={theme[season].focusColor}
                        $textColor={theme[season].textColor}
                        $hoverBgColor={theme[season].focusColor}
                    >
                        중복검사
                    </DuplicateTestBtn>
                </BtnSection>

                {/* 성별 선택 */}
                <CheckboxWrapper>
                    <div className="form-check">
                        <input
                            className="form-check-input"
                            type="radio"
                            name="sex"
                            id="radioDefault1"
                            value="men"
                            checked={selectedRadio === "men"}
                            onChange={(e) => setSelectedRadio(e.target.value)}
                        />
                        <label className="form-check-label" htmlFor="radioDefault1">
                            남자
                        </label>
                    </div>
                    &nbsp;&nbsp;
                    <div className="form-check">
                        <input
                            className="form-check-input"
                            type="radio"
                            name="sex"
                            id="radioDefault2"
                            value="women"
                            checked={selectedRadio === "women"}
                            onChange={(e) => setSelectedRadio(e.target.value)}
                        />
                        <label className="form-check-label" htmlFor="radioDefault2">
                            여자
                        </label>
                    </div>
                </CheckboxWrapper>

                {/* 회원가입 버튼 */}
                <LoginButton
                    type="submit"
                    style={{ marginBottom: '20px' }}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    수정하기
                </LoginButton>
            </LoginSection>
        </RegisterWrapper>
    );
};

export default MyPageInfo;
