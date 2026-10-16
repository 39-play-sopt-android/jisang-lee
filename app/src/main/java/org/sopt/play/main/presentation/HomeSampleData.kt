package org.sopt.play.main.presentation

import org.sopt.play.R
import org.sopt.play.main.presentation.model.HomeProduct

object HomeSampleData {

    val userName: String = "이지상"

    val menus: List<String> = listOf(
        "2배적립",
        "홈",
        "셀렉티드",
        "브랜드",
        "뷰티",
        "라이프",
        "랭킹"
    )

    val categories: List<String> = listOf(
        "전체",
        "상의",
        "팬츠",
        "원피스",
        "아우터",
        "스커트",
        "슈즈",
        "가방",
        "이너웨어/잠옷",
        "폰꾸미기"
    )

    val bannerImages: List<Int> = listOf(
        R.drawable.img_home_banner_01,
        R.drawable.img_home_banner_02,
        R.drawable.img_home_banner_03,
    )

    val products: List<HomeProduct> = listOf(
        HomeProduct(
            id = "product_01",
            imageRes = R.drawable.img_home_product_01,
            storeName = "트랜디어패럴",
            productName = "[카프리1위🏆/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
            discountPercent = 36,
            price = 28_800
        ),

        HomeProduct(
            id = "product_02",
            imageRes = R.drawable.img_home_product_02,
            storeName = "슬로우앤드",
            productName = "[🍁가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
            discountPercent = 50,
            price = 26_820,
            isFavorite = true
        ),

        HomeProduct(
            id = "product_03",
            imageRes = R.drawable.img_home_product_03,
            storeName = "어텀",
            productName = "보울7부티셔츠",
            discountPercent = 10,
            price = 17_100
        ),

        HomeProduct(
            id = "product_04",
            imageRes = R.drawable.img_home_product_04,
            storeName = "슬로우앤드",
            productName = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
            discountPercent = 40,
            price = 35_200
        )
    )
}