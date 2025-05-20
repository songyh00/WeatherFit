import React, { useState, useEffect } from "react";
import '../App.css';
import {
    Container,
    Section,
    Title,
    InfoItem,
    StyledLink,
    Emoji,
    Carousel,
    Photo,
    Footer,
    SectionTitle,
    CategoryTitle
} from "../layout/Mypage.style.js";
import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";


const MyPage = () => {


    const [id, setId] = useState("tester user");
    const [email, setEmail] = useState("test@gmail.com");
    const [gender, setGender] = useState("남자");

    const navigate = useNavigate(); // ✅ 페이지 이동용

    const handleSubmit = () => {
        if (window.confirm("정말로 회원 탈퇴하시겠습니까?")) {
            // 탈퇴 API 호출 성공 시
            alert("회원 탈퇴가 완료되었습니다.");
            navigate("/");
        }
    };


    return (
        <Container>
            <Section>
                <Title>[ 마이페이지 ]</Title>
            </Section>

            <Section>
                <SectionTitle>📌 내 정보</SectionTitle>
                <InfoItem><span>🔸 아이디:</span> {id}</InfoItem>
                <InfoItem><span>📧 이메일:</span> {email}</InfoItem>
                <InfoItem><span>🚹🚺 성별:</span> {gender}</InfoItem>
                <InfoItem>
                    <Emoji>🛠️</Emoji>
                    <StyledLink to="/MyPageInfo">
                        [내 정보 수정]
                    </StyledLink>
                    <Emoji>🔒</Emoji>
                    <StyledLink to="/MyPagePassword">
                        [비밀번호 변경하기]
                    </StyledLink>
                </InfoItem>
            </Section>

            <Section>
                <SectionTitle>❤️ 내가 좋아요 누른 목록</SectionTitle>

                {/*캐러셀 구현하기*/}
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
