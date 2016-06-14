@file:JvmName("pressures")

package com.github.debop.siunits

enum class PressureUnit {
  /**
   * 기압 :  지구 해수면 근처에서 잰 대기압을 기준으로 한다. 1기압은 101325 Pa, 760 mmHg이다.
   */
  Atm,
  /**
   * Pascal 단위 ( N/m2 ) : https://ko.wikipedia.org/wiki/%ED%8C%8C%EC%8A%A4%EC%B9%BC_(%EB%8B%A8%EC%9C%84)
   */
  Pascal,
  /**
   * HectoPascal (1 hPa = 100 pa)
   */
  HectoPascal,
  /**
   * KiloPascal (1kPa = 1000 Pa)
   */
  KiloPascal,

  /**
   * MegaPascal (1 MPa = 1000_000 Pa)
   */
  MegaPascla,

  /**
   * Bar : 1 bar = 100,000 Pa
   *
   *
   * 대기압은 통상적으로 밀리바를 사용하여, "표준" 해수면 압력을 1.01325 바와 같은 1013.25 밀리바(hPa, 헥토파스칼)로 정의한다.
   * 밀리바는 SI 단위계가 아닌데도 불구하고, 아직 기상학 분야에서 대기압을 기술하는 압력 단위로 사용되곤 한다.
   */
  BAR,

  /**
   * Deci Bar : 1 dbar = 0.1 bar = 10,000 Pa
   *
   *
   * 대기압은 통상적으로 밀리바를 사용하여, "표준" 해수면 압력을 1.01325 바와 같은 1013.25 밀리바(hPa, 헥토파스칼)로 정의한다.
   * 밀리바는 SI 단위계가 아닌데도 불구하고, 아직 기상학 분야에서 대기압을 기술하는 압력 단위로 사용되곤 한다.
   */
  DeciBar,

  /**
   * Milli Bar : 1 mbar = 0.001 bar = 100 Pa
   *
   *
   * 대기압은 통상적으로 밀리바를 사용하여, "표준" 해수면 압력을 1.01325 바와 같은 1013.25 밀리바(hPa, 헥토파스칼)로 정의한다.
   * 밀리바는 SI 단위계가 아닌데도 불구하고, 아직 기상학 분야에서 대기압을 기술하는 압력 단위로 사용되곤 한다.
   */
  MilliBar,

  /**
   * 제곱 인치당 파운드(영어: pound per square inch, 정확하게는 영어: pound-force per square inch,
   * 기호는 psi, lbf/in2, lbf/in2, lbf/sq in, lbf/sq in)는 애버더포와 단위로 나타낸 압력이나 응력 단위다.
   * 이 단위는 1 제곱 인치 넓이에 1 파운드힘이 누르는 압력이다. 1 제곱 인치당 파운드 는 약 6894.757 Pa이다.
   */
  PSI,

  /**
   * 토르(torr, 기호 Torr)는 압력의 단위로 1mm의 수은주 압력에 해당한다.
   * 1 Torr = 1 mmHg. 1기압은 760 mmHg 이므로 1 Torr는 1 기압의 1/760이다. 이탈리아의 과학자 에반젤리스타 토리첼리의 이름을 따서 만들어졌다.
   */
  Torr,

  /**
   * 수은주 밀리미터(mmHg)는 압력의 단위로 1mmHg는 수은주의 높이가 1mm일 때의 압력이다. 1기압은 약 760mmHg에 해당한다.
   */
  mmHg,
}