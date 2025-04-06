import React, { useState, useEffect, useRef } from "react";
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import { Link } from "react-router-dom";
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";
import { BtnSection, DuplicateTestBtn, RegisterWrapper } from "../layout/JoinTheMembership.style.js";

const JoinTheMembership = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    // 중복확인 결과를 저장할 상태 추가
    const [isUserNameAvailable, setIsUserNameAvailable] = useState(false);
    const [isEmailAvailable, setIsEmailAvailable] = useState(false);

    // input에 포커스 주기 위한 ref 생성
    const userNameRef = useRef(null);
    const emailRef = useRef(null);
    const passwordRef = useRef(null);

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24); // 하루마다 업데이트
        return () => clearInterval(interval);
    }, []);

    const checkUserNameAvailability = () => {
        // 사용자명 중복 확인
        if(userName.trim() === "") {
            alert("사용자명을 입력해주세요.");
            userNameRef.current?.focus();
            return;
        }

        // 중복 검사 성공
        setIsUserNameAvailable(true);
        alert("사용 가능한 사용자명입니다.");
    };

    const checkUserEmailAvailability = () => {
        // 이메일 입력 확인
        if(email.trim() === "") {
            alert("이메일을 입력해주세요.");
            emailRef.current?.focus();
            return;
        }

        // 이메일 형식 유효성 검사
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("유효한 이메일 주소를 입력해주세요.");
            emailRef.current?.focus();
            return;
        }

        // 중복 검사 성공
        setIsEmailAvailable(true);
        alert("사용 가능한 이메일입니다.");
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // 입력값이 모두 채워졌는지 확인하고 포커스 지정
        if(userName.trim() === "") {
            alert("사용자명을 입력해주세요.");
            userNameRef.current?.focus();
            return;
        } else if(email.trim() === "") {
            alert("이메일을 입력해주세요.");
            emailRef.current?.focus();
            return;
        } else if(password.trim() === "") {
            alert("비밀번호를 입력해주세요.");
            passwordRef.current?.focus();
            return;
        }

        // 중복 확인이 진행되었는지 확인
        if(!isUserNameAvailable) {
            alert("사용자명 중복 확인을 해주세요.");
            userNameRef.current?.focus();
            return;
        }
        if(!isEmailAvailable) {
            alert("이메일 중복 확인을 해주세요.");
            emailRef.current?.focus();
            return;
        }

        // 여기서 데이터베이스에 사용자 정보등록하시면 될거같아요

        alert("회원가입이 완료되었습니다.");
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
                        style={{ width: '356px' }}
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        borderColor={theme[season].borderColor}
                        bgColor={theme[season].bgColor}
                        focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
                        placeholder="사용자명을 입력해주세요"
                    />
                    <DuplicateTestBtn
                        type="button"
                        onClick={checkUserNameAvailability}
                        borderColor={theme[season].borderColor}
                        bgColor={theme[season].bgColor}
                        focusColor={theme[season].focusColor}
                        textColor={theme[season].textColor}
                        hoverBgColor={theme[season].focusColor}
                    >
                        중복검사
                    </DuplicateTestBtn>
                </BtnSection>

                <BtnSection>
                    <LoginInput
                        ref={emailRef}
                        type="email"
                        style={{ width: '356px' }}
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        borderColor={theme[season].borderColor}
                        bgColor={theme[season].bgColor}
                        focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
                        placeholder="이메일을 입력해주세요"
                    />
                    <DuplicateTestBtn
                        type="button"
                        onClick={checkUserEmailAvailability}
                        borderColor={theme[season].borderColor}
                        bgColor={theme[season].bgColor}
                        focusColor={theme[season].focusColor}
                        textColor={theme[season].textColor}
                        hoverBgColor={theme[season].focusColor}
                    >
                        중복검사
                    </DuplicateTestBtn>
                </BtnSection>

                <LoginInput
                    ref={passwordRef}
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="비밀번호를 입력해주세요"
                />
                <LoginButton
                    type="submit"
                    style={{ marginBottom: '20px' }}
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={theme[season].focusColor}
                    hoverBgColor={theme[season].focusColor}
                >
                    회원가입
                </LoginButton>
            </LoginSection>
        </RegisterWrapper>
    );
};

export default JoinTheMembership;