$('#users a').editable({
    type: 'text',
    name: 'username',
    url: '/post',
    title: 'Enter username'
});

//ajax emulation
$.mockjax({
    url: '/post',
    responseTime: 200
}); 
