import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.ExcludeCategories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SuperBankingAppOrSomethingTest {

    private Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account( );
    }

    @org.junit.Test
    public void A_new_account_is_empty() throws Exception {
        assertThat(account.isEmpty(), equalTo( true ));
    }

    @Test
    public void We_can_withdraw_from_an_account() throws Exception {
        account.deposit( new Money(10));

        account.withdraw( new Money(1) );
    }

    @Test
    public void An_account_cannot_be_overdrawn() throws Exception {
        Money money = new Money(1);
        try {
            account.withdraw( money );
        } catch (AccountCannotBeOverdrawn e) {
            return;
        }
        fail("Expected AccountCannotBeOverdrawn");
    }

    private class Account {
        private boolean hasDeposited;

        public boolean isEmpty() {
            return true;
        }


        public void withdraw(Money money) {
            if( !hasDeposited ){
                throw new AccountCannotBeOverdrawn();
            }
        }

        public void deposit(Money money) {
            hasDeposited = true;
        }
    }

    private class AccountCannotBeOverdrawn extends RuntimeException {
    }

    private class Money {
        public Money(int i) {

        }
    }
}
