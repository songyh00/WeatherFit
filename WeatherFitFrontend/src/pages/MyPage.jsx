
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

import React from "react";
import logo from '../assets/logo.png';
import CloseBtnLogo from "../components/CloseBtnLogo.jsx";
import {ModalCloseBtn} from "../layout/WeatherSection.style.js";

const testImages = [
    { src: "1231" },
    { src: logo },
    { src: logo },
    { src: logo },
    { src: logo },
    { src: logo },
    { src: logo },
];

const ImageSlider = ({ title, url }) => {
    const [images, setImages] = useState([]);
    const [modalImage, setModalImage] = useState(null); // 모달에 띄울 이미지 상태

    // 모달 닫기
    const closeModal = () => setModalImage(null);


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
                loopedSlides={images.length}
                spaceBetween={10}
                modules={[Navigation, Pagination]}
                navigation
                pagination={false}
                style={{ width: '800px' }}
            >
                {testImages.map((img, idx) => (
                    <SwiperSlide key={idx}>
                        <img
                            src={img.src}
                            alt={`slide-${idx}`}
                            style={{
                                width: '100%',
                                height: '200px',
                                objectFit: 'cover',
                                display: 'block',
                                cursor: 'pointer',
                            }}
                            onClick={() => setModalImage(img.src)}
                        />
                    </SwiperSlide>
                ))}
            </Swiper>
            {/* 모달 */}
            {modalImage && (
                <div
                    onClick={closeModal}
                    style={{
                        position: 'fixed',
                        top: 0, left: 0, right: 0, bottom: 0,
                        backgroundColor: 'rgba(0,0,0,0.8)',
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        zIndex: 1000,
                    }}
                >
                    <img
                        src={modalImage}
                        alt="modal"
                        style={{
                            width: '60vw',
                            height: 'auto',
                            maxHeight: '70vh',
                            borderRadius: '12px',
                            boxShadow: '0 0 20px rgba(255,255,255,0.8)',
                        }}
                        onClick={e => e.stopPropagation()} // 모달 이미지 클릭 시 닫기 방지
                    />
                    <ModalCloseBtn
                        onClick={closeModal}
                        style={{
                            position: 'absolute',
                            top: 20,
                            right: 20,
                            fontSize: '2rem',
                            color: 'white',
                            background: 'transparent',
                            border: 'none',
                            cursor: 'pointer',
                        }}
                    >
                        <CloseBtnLogo />
                    </ModalCloseBtn>
                </div>
            )}
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
                </div><br /><br />
                <div>
                    <ImageSlider title="👔 상의" url="/api/images/top" />
                </div><br /><br />
                <div>
                    <ImageSlider title="👖 하의" url="/api/images/bottom" />
                </div>
            </Section>

            <Footer onClick={handleSubmit}>
                🏠 [회원 탈퇴하기]
            </Footer><br /><br />
        </Container>
    );
};

export default MyPage;
