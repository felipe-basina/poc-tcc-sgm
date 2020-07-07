package poc.tcc.sgm.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719885157711820011L;
	
	private UserOutDTO userOutDTO;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(this.userOutDTO.getProfile())));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.userOutDTO.getPassword();
	}

	@Override
	public String getUsername() {
		return this.userOutDTO.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
