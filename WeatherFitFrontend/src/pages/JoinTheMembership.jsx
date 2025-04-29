import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";
import { BtnSection, CheckboxWrapper, DuplicateTestBtn, RegisterWrapper } from "../layout/JoinTheMembership.style.js";

const JoinTheMembership = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [selectedRadio, setSelectedRadio] = useState("MALE");
    const [isUserNameAvailable, setIsUserNameAvailable] = useState(false);
    const [isEmailAvailable, setIsEmailAvailable] = useState(false);

    const userNameRef = useRef(null);
    const emailRef = useRef(null);
    const passwordRef = useRef(null);
    const navigate = useNavigate();

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
            const response = await axios.get(`/api/auth/check-username?username=${userName}`);
            const isTaken = response.data;
            if (isTaken) {
                setIsUserNameAvailable(false);
                alert("이미 사용 중인 아이디입니다.");
            } else {
                setIsUserNameAvailable(true);
                alert("사용 가능한 아이디입니다.");
            }
        } catch (error) {
            console.error(error);
            setIsUserNameAvailable(false);
            alert("아이디 중복 검사 중 오류가 발생했습니다.");
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
            const response = await axios.get(`/api/auth/check-email?email=${email}`);
            const isTaken = response.data;
            if (isTaken) {
                setIsEmailAvailable(false);
                alert("이미 사용 중인 이메일입니다.");
            } else {
                setIsEmailAvailable(true);
                alert("사용 가능한 이메일입니다.");
            }
        } catch (error) {
            console.error(error);
            setIsEmailAvailable(false);
            alert("이메일 중복 검사 중 오류가 발생했습니다.");
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
        } else if (password.trim() === "") {
            alert("비밀번호를 입력해주세요.");
            passwordRef.current?.focus();
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
            const gender = selectedRadio;

            await axios.post("/api/auth/signup", {
                username: userName,
                password,
                email,
                gender,
            });

            alert("회원가입이 완료되었습니다.");

            const response = await axios.post("/api/auth/login", {
                username: userName,
                password: password,
            });

            const { token, username: loggedInUsername } = response.data;
            localStorage.setItem("token", token);
            localStorage.setItem("username", loggedInUsername);

            navigate("/");
        } catch (error) {
            console.error(error);
            alert(error.response?.data?.message || "회원가입 실패");
        }
    };

    return (
        <RegisterWrapper onSubmit={handleSubmit}>
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>

            <LoginSection>
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

                <LoginInput
                    ref={passwordRef}
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="비밀번호를 입력해주세요"
                />

                <CheckboxWrapper>
                    <div className="form-check">
                        <input
                            className="form-check-input"
                            type="radio"
                            name="sex"
                            id="radioDefault1"
                            value="MALE"
                            checked={selectedRadio === "MALE"}
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
                            value="FEMALE"
                            checked={selectedRadio === "FEMALE"}
                            onChange={(e) => setSelectedRadio(e.target.value)}
                        />
                        <label className="form-check-label" htmlFor="radioDefault2">
                            여자
                        </label>
                    </div>
                </CheckboxWrapper>

                <LoginButton
                    type="submit"
                    style={{ marginBottom: '20px' }}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    회원가입
                </LoginButton>
            </LoginSection>
        </RegisterWrapper>
    );
};

export default JoinTheMembership;
