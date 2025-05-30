
import '../App.css';
import {
    Container,
    Section,
    Footer,
    SectionTitle, GlobalStyle
} from "../layout/Mypage.style.js";
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination } from 'swiper/modules';
import { useEffect, useState } from 'react';
import logo from "../assets/logo.png"

import React from "react";

const ImageSlider = ({ title, url }) => {
    const [images, setImages] = useState([]);

    useEffect(() => {
        fetch(url)
            .then(res => res.json())
            .then(data => setImages(data));
    }, [url]);

    return(
        <div>
            <h2>{title}</h2>
            <Swiper
                slidesPerView={5}
                slidesPerGroup={1}
                loop={true}
                loopedSlides={images.length}  // 또는 고정 숫자 (예: 7)
                spaceBetween={10}
                modules={[Navigation, Pagination]}
                navigation
                pagination={false}
                style={{ width: '800px' }}
            >
                {/*{images.map((img, idx) => (*/}
                {/*    <SwiperSlide key={idx}>*/}
                {/*        <img*/}
                {/*            src={img.image_url}*/}
                {/*            alt={`slide-${idx}`}*/}
                {/*            style={{*/}
                {/*                width: '100%',        // Swiper가 슬라이드 크기를 자동 계산*/}
                {/*                height: '200px',*/}
                {/*                objectFit: 'cover',*/}
                {/*                borderRadius: '8px'*/}
                {/*            }}*/}
                {/*        />*/}
                {/*    </SwiperSlide>*/}
                {/*))}*/}
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>
                <SwiperSlide>
                    <img
                        src={logo}
                        style={{
                            width: '100%',
                            height: '200px',     // 원하는 높이
                            objectFit: 'cover',  // 또는 contain
                            display: 'block'
                        }}
                    />
                </SwiperSlide>

            </Swiper>
        </div>
    );
}

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
    return (
        <Container>
            <GlobalStyle />
            <Section>
                <SectionTitle>❤️ 내가 좋아요 누른 목록</SectionTitle>
                {/* 좋아요 이미지 파트는 동일하게 유지 */}
                <div>
                    <ImageSlider title="👕 아우터" url="/api/images/outer" />
                </div>
                <div>
                    <ImageSlider title="👔 상의" url="/api/images/top" />
                </div>
                <div>
                    <ImageSlider title="👖 하의" url="/api/images/bottom" />
                </div>
            </Section>

            <Footer onClick={handleSubmit}>
                🏠 [회원 탈퇴하기]
            </Footer>
        </Container>
    );
};

export default MyPage;
