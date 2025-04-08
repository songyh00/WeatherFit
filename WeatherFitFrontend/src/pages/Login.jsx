import React, { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom"; // Link도 여기로 같이 정리
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import logo from "../assets/logo.png";
import axios from "axios";

// 계절 판별 함수
const getSeason = () => {
    const month = new Date().getMonth() + 1;
    if (month >= 3 && month <= 5) return "spring";
    if (month >= 6 && month <= 8) return "summer";
    if (month >= 9 && month <= 11) return "autumn";
    return "winter";
};

// 계절별 테마 색상
const theme = {
    spring: { borderColor: "#FFB6C1", bgColor: "#FFF0F5", focusColor: "#FF69B4", textColor: "#000" },
    summer: { borderColor: "#4FC3F7", bgColor: "#E0F7FA", focusColor: "#0288D1", textColor: "#000" },
    autumn: { borderColor: "#FF8A65", bgColor: "#FFF3E0", focusColor: "#D84315", textColor: "#000" },
    winter: { borderColor: "#90A4AE", bgColor: "#ECEFF1", focusColor: "#455A64", textColor: "#000" }
};

const Login = () => {
    const navigate = useNavigate();
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    // 계절 업데이트
    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    const handleLogin = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/login", {
                username,
                password
            });

            if (response.data.success) {
                alert("로그인 성공!");
                localStorage.setItem("isLoggedIn", "true"); // 로그인 상태 저장
                navigate("/"); // 메인페이지로 이동
            } else {
                alert("아이디 또는 비밀번호가 틀렸습니다.");
            }
        } catch (error) {
            console.error("로그인 요청 실패:", error);
            alert("서버 오류가 발생했습니다.");
        }
    };

    return (
        <LoginWrapper>
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>
            <LoginSection>
                <LoginInput
                    style={{ marginTop: '20px' }}
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="아이디를 입력해주세요"
                />
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
                    onClick={handleLogin}
                    borderColor={theme[season].borderColor}
                    bgColor={theme[season].bgColor}
                    focusColor={theme[season].focusColor}
                    textColor={theme[season].textColor}
                    hoverBgColor={theme[season].focusColor}
                >
                    로그인
                </LoginButton>
            </LoginSection>
        </LoginWrapper>
    );
};

export default Login;
