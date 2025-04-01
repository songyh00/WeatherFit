import React, { useState, useEffect } from "react";
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
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // 여기에 데이터를 서버로 전송하는 코드 작성
        alert("회원가입이 완료되었습니다.");
    };

    return (
        <RegisterWrapper>
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>

            <LoginSection onSubmit={handleSubmit}>
                <BtnSection style={{ marginTop: '5px' }}>
                    <LoginInput
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
                        type="text"
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

                <LoginInput
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