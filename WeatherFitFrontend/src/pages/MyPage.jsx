
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
    CategoryTitle,
    CarouselContainer,
    ArrowButton
} from "../layout/Mypage.style.js";

import logo from "../assets/logo.png";
import React from "react";



const MyPage = () => {


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





    const CarouselWrapper = ({ children }) => {
        const carouselRef = React.useRef(null);
        const itemWidth = 114; // 100px (photo width) + 14px (gap)

        const scrollLeft = () => {
            if (carouselRef.current) {
                carouselRef.current.scrollBy({ left: -itemWidth * 3, behavior: "smooth" });
            }
        };

        const scrollRight = () => {
            if (carouselRef.current) {
                carouselRef.current.scrollBy({ left: itemWidth * 3, behavior: "smooth" });
            }
        };

        return (
            <CarouselContainer>
                <ArrowButton onClick={scrollLeft} position="left">◀</ArrowButton>
                <Carousel ref={carouselRef}>
                    {children}
                </Carousel>
                <ArrowButton onClick={scrollRight} position="right">▶</ArrowButton>
            </CarouselContainer>
        );
    };



    return (
        <Container>
            <Section>
                <SectionTitle>❤️ 내가 좋아요 누른 목록</SectionTitle>
                {/* 좋아요 이미지 파트는 동일하게 유지 */}
                <div>
                    <CategoryTitle>👕 아우터</CategoryTitle>
                    <CarouselWrapper>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </CarouselWrapper>
                </div>
                <div>
                    <CategoryTitle>👔 상의</CategoryTitle>
                    <CarouselWrapper>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </CarouselWrapper>
                </div>
                <div>
                    <CategoryTitle>👖 하의</CategoryTitle>
                    <CarouselWrapper>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                        <Photo><img src={logo} alt="WeatherFit Logo" /></Photo>
                    </CarouselWrapper>
                </div>
            </Section>

            <Footer onClick={handleSubmit}>
                🏠 [회원 탈퇴하기]
            </Footer>
        </Container>
    );
};

export default MyPage;
