package com.react.exam.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 단위테스트(db관련 bean이 IOC에 등록되면됨)
 * @DataJpaTest : Repository들을 다 IOC에 등록하여 DI해줌,
 *              : JpaRepository를 상속하는 Interface이기때문에 다른 service나 repository 끌고올 필요없음
 * @AutoConfigureTestDatabase : 실제DB쓸건지 가짜DB쓸건지(Replace.NONE : 실제DB)
 */
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class BookRepositoryUnitTest {

    //@Mock 이 필요없음. 이미 ServiceTest에 @Mock으로 IOC에 띄워져있음
    @Autowired
    private BookRepository bookRepository;
}
