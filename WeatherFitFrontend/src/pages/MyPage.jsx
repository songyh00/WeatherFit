import React, { useState, useEffect } from "react";
import '../App.css';
import {
    Container, Section, Title, InfoItem, StyledLink,
    Emoji, Carousel, Photo, Footer, SectionTitle, CategoryTitle
} from "../layout/Mypage.style.js";
import { Button } from "../layout/Mypage_info.style.js";
import logo from "../assets/logo.png";

const MyPage = () => {
    const [id, setId] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");

    useEffect(() => {
        fetch("/api/user/profile", {
            method: "GET",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("사용자 정보 조회 실패");
                return res.json();
            })
            .then(data => {
                setId(data.username);
                setEmail(data.email);
                if (data.gender === "남자") setGender("남자");
                else if (data.gender === "여자") setGender("여자");
                else setGender("기타");
            })
            .catch(err => {
                alert("사용자 정보를 불러올 수 없습니다.");
                console.error(err);
            });
    }, []);

    const handleSubmit = () => {
        if (!window.confirm("정말로 회원 탈퇴하시겠습니까? 탈퇴 후 계정은 복구할 수 없습니다.")) return;

        fetch("/api/auth/withdraw", {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("회원 탈퇴에 실패했습니다.");
                }

                alert("회원 탈퇴가 완료되었습니다.");
                localStorage.removeItem("token");
                window.location.href = "/"; // 홈 또는 로그인 페이지로 이동
            })
            .catch(err => {
                alert("❌ " + err.message);
                console.error("회원 탈퇴 오류:", err);
            });
    };

    return (
        <Container>
            <Section>
                <Title>[ 마이페이지 ]</Title>
            </Section>

            <Section>
                <SectionTitle>📌 프로필</SectionTitle>
                <InfoItem><span>🔸 아이디:</span> {id}</InfoItem>
                <InfoItem><span>📧 이메일:</span> {email}</InfoItem>
                <InfoItem>
                    <span>{gender === "남자" ? "🚹" : "🚺"} 성별:</span> {gender}
                </InfoItem>
                <InfoItem>
                    <Emoji>🛠️</Emoji>
                    <StyledLink to="/MyPageInfo">[프로필 수정]</StyledLink>
                    <Emoji>🔒</Emoji>
                    <StyledLink to="/MyPagePassword">[비밀번호 변경]</StyledLink>
                </InfoItem>
            </Section>

            <Section>
                <SectionTitle>❤️ 내가 좋아요 누른 목록</SectionTitle>
                {/* 좋아요 이미지 파트는 동일하게 유지 */}
                <div>
                    <CategoryTitle>👕 아우터</CategoryTitle>
                    <Carousel>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </Carousel>
                </div>
                <div>
                    <CategoryTitle>👔 상의</CategoryTitle>
                    <Carousel>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </Carousel>
                </div>
                <div>
                    <CategoryTitle>👖 하의</CategoryTitle>
                    <Carousel>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </Carousel>
                </div>
            </Section>

            <Footer onClick={handleSubmit}>
                🏠 [회원 탈퇴하기]
            </Footer>
        </Container>
    );
};

export default MyPage;
