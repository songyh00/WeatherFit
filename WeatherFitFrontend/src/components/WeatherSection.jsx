import React, {useState, useRef, useEffect} from "react";
import {
    WeatherCardWrap,
    RegionText,
    IconAndTempRow,
    IconBox,
    TemperatureBig,
    TimeLabel,
    ModalContainer,
    ModalOverlay,
    ModalCloseBtn,
    WeatherCardRow,
    WeatherTextMain,
    HorizontalScrollContainer,
    HourCard, TimeText, TempText, WeatherTextHour
} from "../layout/WeatherSection.style.js";
import WeatherDisplay from "./WeatherType.jsx";
import CloseBtnLogo from "./CloseBtnLogo.jsx";

const WeatherSection = ({ weatherData, address }) => {
    const [modalOpen, setModalOpen] = useState(false);

    const defaultAddress = "서울시 종로구";
    const targetAddress = address?.trim() ? address : defaultAddress;
    const scrollRefToday = useRef(null);
    const scrollRefTomorrow = useRef(null);

    useEffect(() => {
        if (typeof document !== "undefined") {
            if (modalOpen) {
                document.body.style.overflow = "hidden";
            } else {
                document.body.style.overflow = "";
            }
            return () => {
                document.body.style.overflow = "";
            };
        }
    }, [modalOpen]);

    // 수직 휠 스크롤을 가로 스크롤로 변경하는 함수
    const handleWheel = (e) => {
        e.preventDefault();
        const container = e.currentTarget;
        const scrollLeft = container.scrollLeft;
        const maxScrollLeft = container.scrollWidth - container.clientWidth;

        // 가로 스크롤이 맨 왼쪽이고 왼쪽으로 스크롤 시도하거나
        // 맨 오른쪽이고 오른쪽으로 스크롤 시도하면
        // 스크롤 이벤트를 부모로 전달하지 않도록 막기
        if (
            (e.deltaY < 0 && scrollLeft === 0) ||
            (e.deltaY > 0 && scrollLeft === maxScrollLeft)
        ) {
            e.stopPropagation();
        } else {
            container.scrollLeft += e.deltaY;
        }
    };

    const now = new Date();
    const hour = now.getHours();
    const fcstTime = String(hour).padStart(2, '0') + '00';

    // 오늘 날짜 문자열
    const todayStr = {
        month: String(now.getMonth() + 1).padStart(2, "0"),
        day: String(now.getDate()).padStart(2, "0"),
    };

    // 내일 날짜 문자열
    const tomorrow = new Date(now);
    tomorrow.setDate(now.getDate() + 1);

    const tomorrowStr = {
        month: String(tomorrow.getMonth() + 1).padStart(2, "0"),
        day: String(tomorrow.getDate()).padStart(2, "0"),
    };

    const todayData = weatherData?.hourlyTemperatures?.filter(item => {
        const isToday = item.month === todayStr.month && item.day === todayStr.day;
        const itemHour = parseInt(item.time.slice(0, 2));
        return isToday && itemHour >= hour;
    });

    const tomorrowData = weatherData?.hourlyTemperatures?.filter(item => {
        return item.month === tomorrowStr.month && item.day === tomorrowStr.day;
    });

    const currentWeather = weatherData?.hourlyTemperatures?.find(w =>
        w.month === todayStr.month && w.day === todayStr.day && w.time === fcstTime
    );

    if (!currentWeather) {
        return <WeatherCardWrap>날씨 정보를 불러올 수 없습니다.</WeatherCardWrap>;
    }

    const displayTime = `${todayStr.month}월 ${todayStr.day}일 ${hour < 12 ? '오전' : '오후'} ${hour % 12 === 0 ? 12 : hour % 12}시`;



    return (
        <>
            <WeatherCardWrap onClick={() => setModalOpen(true)} >
                <RegionText>{targetAddress}</RegionText>
                <WeatherTextMain>{currentWeather.weatherType}</WeatherTextMain>
                <IconAndTempRow>
                    <IconBox>
                        <WeatherDisplay weatherType={currentWeather.weatherType} />
                    </IconBox>
                    <TemperatureBig>{currentWeather.temperature}°</TemperatureBig>
                </IconAndTempRow>
                <TimeLabel>{displayTime}</TimeLabel>
            </WeatherCardWrap>

            {modalOpen && (
                <>
                    <ModalOverlay onClick={() => setModalOpen(false)} />
                    <ModalContainer>
                        <div style={{ position: "relative" }}><br />
                            <h3 style={{textAlign: 'center'}}>오늘</h3>
                            <HorizontalScrollContainer
                                ref={scrollRefToday}
                                onWheel={handleWheel} // 여기서 수직 휠을 가로 스크롤로 변경
                            >
                                {todayData?.map((item, index) => (
                                    <HourCard key={index}>
                                        <TimeText>{parseInt(item.time.slice(0, 2))}시</TimeText>
                                        <WeatherDisplay weatherType={item.weatherType} />
                                        <TempText>{item.temperature}°</TempText>
                                        <WeatherTextHour>{item.weatherType}</WeatherTextHour>
                                    </HourCard>
                                ))}
                            </HorizontalScrollContainer><br />

                            <h3 style={{textAlign: 'center'}}>내일</h3>
                            <HorizontalScrollContainer
                                ref={scrollRefTomorrow}
                                onWheel={handleWheel} // 내일 데이터도 동일하게 처리
                            >
                                {tomorrowData?.map((item, index) => (
                                    <HourCard key={index}>
                                        <TimeText>{parseInt(item.time.slice(0, 2))}시</TimeText>
                                        <WeatherDisplay weatherType={item.weatherType} />
                                        <TempText>{item.temperature}°</TempText>
                                        <WeatherTextHour>{item.weatherType}</WeatherTextHour>
                                    </HourCard>
                                ))}
                            </HorizontalScrollContainer>

                            <ModalCloseBtn onClick={() => setModalOpen(false)}>
                                <CloseBtnLogo />
                            </ModalCloseBtn>
                        </div>
                    </ModalContainer>
                </>
            )}
        </>
    );
};

export default WeatherSection;
