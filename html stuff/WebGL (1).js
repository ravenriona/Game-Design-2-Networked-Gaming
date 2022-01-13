class WebGL {
  constructor() {
    this.vertexShaderSource = document.getElementById("vertex-shader").text;
    this.fragmentShaderSource = document.getElementById("fragment-shader").text;
    this.vertexShader = this.createShader(gl.VERTEX_SHADER, this.vertexShaderSource);
    this.fragmentShader = this.createShader(gl.FRAGMENT_SHADER, this.fragmentShaderSource);
    this.program = this.createProgram(this.vertexShader, this.fragmentShader);
    
    gl.viewport(0,0, gl.canvas.width, gl.canvas.height);
    
    gl.clearColor(1,1,1,1);
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
    gl.enable(gl.DEPTH_TEST);
    gl.useProgram(this.program);
  }
  
  createShader(type, source) {
    var shader = gl.createShader(type);
    gl.shaderSource(shader, source);
    gl.compileShader(shader);
    var success = gl.getShaderParameter(shader, gl.COMPILE_STATUS);
    if(success)
    	return shader;
    console.warn(gl.getShaderInfoLog(shader));
    gl.deleteShader(shader);
  }
  
  createProgram(vs,fs) {
    var program = gl.createProgram();
    gl.attachShader(program,vs);
    gl.attachShader(program,fs);
    gl.linkProgram(program);
    var success = gl.getProgramParameter(program, gl.LINK_STATUS);
    if(success)
    	return program;
    console.error(gl.getProgramInfoLog(program));
    gl.deleteProgram(program);
  }
}
