package com.teste.loja.config.auth;

//@Component
public class BasicAuthenticationFilterNotUsed {
        //extends OncePerRequestFilter {

//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (header != null && header.startsWith("Basic ")) {
//            String[] tokens = extractAndDecodeHeader(header);
//            assert tokens.length == 2;
//
//            String username = tokens[0];
//            String password = tokens[1];
//
//            Authentication auth = new UsernamePasswordAuthenticationToken(new User(username, password, Collections.emptyList()), password);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String[] extractAndDecodeHeader(String header) throws IOException {
//        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
//        byte[] decoded;
//        try {
//            decoded = Base64.getDecoder().decode(base64Token);
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Failed to decode basic authentication token");
//        }
//
//        String token = new String(decoded, StandardCharsets.UTF_8);
//        int delim = token.indexOf(":");
//
//        if (delim == -1) {
//            throw new RuntimeException("Invalid basic authentication token");
//        }
//        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
//    }
}